import React, { useState, useEffect } from 'react';
import styles from './ProximosEventos.module.css';
import useMockService, { Event, SectionContent } from '../../services/mockService';

const ProximosEventos: React.FC = () => {
  // Estados
  const [isEditMode, setIsEditMode] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [sectionContent, setSectionContent] = useState<SectionContent | null>(null);
  const [events, setEvents] = useState<Event[]>([]);
  const [selectedEventId, setSelectedEventId] = useState<number | null>(null);
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [isSaving, setIsSaving] = useState(false);
  const [message, setMessage] = useState({ text: '', type: '' });

  // Serviço mock (será substituído pelo serviço real quando integrado com backend)
  const mockService = useMockService();

  // Carregar dados iniciais
  useEffect(() => {
    const loadInitialData = async () => {
      try {
        setIsLoading(true);
        
        // Carregar conteúdo da seção
        const content = await mockService.getSectionContent('proximos-eventos');
        setSectionContent(content);
        
        // Carregar eventos
        const eventsData = await mockService.getAllEvents();
        setEvents(eventsData);
      } catch (error) {
        console.error('Erro ao carregar dados:', error);
        showMessage('Erro ao carregar dados. Tente novamente mais tarde.', 'error');
      } finally {
        setIsLoading(false);
      }
    };
    
    loadInitialData();
  }, []);

  // Selecionar evento para edição
  const handleSelectEvent = (eventId: number) => {
    setSelectedEventId(eventId);
  };

  // Mostrar mensagem temporária
  const showMessage = (text: string, type: 'success' | 'error' | '') => {
    setMessage({ text, type });
    if (text) {
      setTimeout(() => setMessage({ text: '', type: '' }), 3000);
    }
  };

  // Funções de edição de conteúdo
  const handleSectionChange = (field: string, value: string) => {
    if (!sectionContent) return;
    
    setSectionContent({
      ...sectionContent,
      [field]: value
    });
  };

  // Funções de edição de eventos
  const handleEventChange = (field: string, value: string) => {
    if (selectedEventId === null) return;
    
    const updatedEvents = events.map(event => {
      if (event.id === selectedEventId) {
        return { ...event, [field]: value };
      }
      return event;
    });
    
    setEvents(updatedEvents);
  };

  // Manipulação de imagem
  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setImageFile(file);
    }
  };

  // Salvar alterações
  const handleSave = async () => {
    if (!sectionContent) return;
    
    setIsSaving(true);
    try {
      // Salvar conteúdo da seção
      await mockService.updateSectionContent('proximos-eventos', sectionContent);
      
      // Salvar eventos modificados
      if (selectedEventId !== null && imageFile) {
        const imageUrl = await mockService.uploadImage(imageFile);
        
        const eventToUpdate = events.find(e => e.id === selectedEventId);
        if (eventToUpdate) {
          await mockService.updateEvent(selectedEventId, {
            ...eventToUpdate,
            imageUrl
          });
          
          // Atualizar eventos na lista local
          const updatedEvents = events.map(event => {
            if (event.id === selectedEventId) {
              return { ...event, imageUrl };
            }
            return event;
          });
          
          setEvents(updatedEvents);
          setImageFile(null);
        }
      }
      
      showMessage('Alterações salvas com sucesso!', 'success');
    } catch (error) {
      console.error('Erro ao salvar alterações:', error);
      showMessage('Erro ao salvar alterações. Tente novamente.', 'error');
    } finally {
      setIsSaving(false);
    }
  };

  // Mostrar spinner durante carregamento inicial
  if (isLoading || !sectionContent) {
    return <div className={styles.loading}>Carregando...</div>;
  }

  return (
    <div className={styles.container}>
      {/* Botão de alternar modo de edição (apenas para desenvolvimento) */}
      <div className={styles.editToggle}>
        <button 
          onClick={() => setIsEditMode(!isEditMode)}
          className={`${styles.editToggleButton} ${isEditMode ? styles.editActive : ''}`}
        >
          {isEditMode ? 'Sair do modo de edição' : 'Editar conteúdo'}
        </button>
      </div>
      
      {/* INTERFACE DE EDIÇÃO */}
      {isEditMode && (
        <div className={styles.editorPanel}>
          <h3 className={styles.editorTitle}>Editor de conteúdo</h3>
          
          {/* Mensagem de feedback */}
          {message.text && (
            <div className={`${styles.message} ${styles[message.type]}`}>
              {message.text}
            </div>
          )}
          
          {/* Editor de seção */}
          <div className={styles.sectionEditor}>
            <h4>Informações da seção</h4>
            <div className={styles.formGroup}>
              <label htmlFor="title">Título:</label>
              <input
                id="title"
                type="text"
                value={sectionContent.title}
                onChange={(e) => handleSectionChange('title', e.target.value)}
                className={styles.textInput}
              />
            </div>
            
            <div className={styles.formGroup}>
              <label htmlFor="subtitle">Subtítulo:</label>
              <textarea
                id="subtitle"
                value={sectionContent.subtitle}
                onChange={(e) => handleSectionChange('subtitle', e.target.value)}
                className={styles.textareaInput}
              />
            </div>
            
            <div className={styles.formGroup}>
              <label htmlFor="buttonText">Texto do botão:</label>
              <input
                id="buttonText"
                type="text"
                value={sectionContent.buttonText}
                onChange={(e) => handleSectionChange('buttonText', e.target.value)}
                className={styles.textInput}
              />
            </div>
          </div>
          
          {/* Editor de eventos */}
          <div className={styles.eventsEditor}>
            <h4>Eventos</h4>
            
            <div className={styles.eventsList}>
              {events.map((event) => (
                <div
                  key={event.id}
                  className={`${styles.eventItem} ${selectedEventId === event.id ? styles.selected : ''}`}
                  onClick={() => handleSelectEvent(event.id)}
                >
                  <span className={styles.eventItemTitle}>{event.title}</span>
                </div>
              ))}
            </div>
            
            {selectedEventId !== null && (
              <div className={styles.eventEditor}>
                <h4>Editar evento</h4>
                
                {/* Encontre o evento selecionado */}
                {(() => {
                  const selectedEvent = events.find(e => e.id === selectedEventId);
                  if (!selectedEvent) return null;
                  
                  return (
                    <>
                      <div className={styles.formGroup}>
                        <label htmlFor="eventTitle">Título do evento:</label>
                        <input
                          id="eventTitle"
                          type="text"
                          value={selectedEvent.title}
                          onChange={(e) => handleEventChange('title', e.target.value)}
                          className={styles.textInput}
                        />
                      </div>
                      
                      <div className={styles.formGroup}>
                        <label htmlFor="eventDescription">Descrição:</label>
                        <textarea
                          id="eventDescription"
                          value={selectedEvent.description}
                          onChange={(e) => handleEventChange('description', e.target.value)}
                          className={styles.textareaInput}
                        />
                      </div>
                      
                      <div className={styles.formGroup}>
                        <label htmlFor="eventInfo">Informações adicionais:</label>
                        <textarea
                          id="eventInfo"
                          value={selectedEvent.additionalInfo}
                          onChange={(e) => handleEventChange('additionalInfo', e.target.value)}
                          className={styles.textareaInput}
                        />
                      </div>
                      
                      <div className={styles.formGroup}>
                        <label htmlFor="eventImage">Imagem:</label>
                        <input
                          id="eventImage"
                          type="file"
                          accept="image/*"
                          onChange={handleImageChange}
                          className={styles.fileInput}
                        />
                        
                        {(selectedEvent.imageUrl || imageFile) && (
                          <div className={styles.imagePreview}>
                            <img 
                              src={imageFile 
                                ? URL.createObjectURL(imageFile) 
                                : selectedEvent.imageUrl} 
                              alt="Preview" 
                            />
                          </div>
                        )}
                      </div>
                    </>
                  );
                })()}
              </div>
            )}
          </div>
          
          <div className={styles.editorActions}>
            <button
              onClick={handleSave}
              disabled={isSaving}
              className={styles.saveButton}
            >
              {isSaving ? 'Salvando...' : 'Salvar alterações'}
            </button>
          </div>
        </div>
      )}
      
      {/* VISUALIZAÇÃO DA SEÇÃO (como será no site) */}
      <section className={styles.proximosEventosSection}>
        <h2 className={styles.title}>{sectionContent.title}</h2>
        <div className={styles.subtitle}>{sectionContent.subtitle}</div>
        
        <div className={styles.cardsContainer}>
          {events.map((event) => (
            <div className={styles.card} key={event.id}>
              <div className={styles.imagePlaceholder}>
                {event.imageUrl ? (
                  <img 
                    src={event.imageUrl} 
                    alt={event.title} 
                    className={styles.eventImage} 
                  />
                ) : '📷'}
              </div>
              <div className={styles.cardContent}>
                <div className={styles.cardTitle}>{event.title}</div>
                <div className={styles.cardDescription}>
                  {event.description}
                </div>
                <div className={styles.cardInfo}>{event.additionalInfo}</div>
              </div>
            </div>
          ))}
        </div>
        
        <div className={styles.verTodos}>
          <button>{sectionContent.buttonText}</button>
        </div>
      </section>
    </div>
  );
};

export default ProximosEventos;