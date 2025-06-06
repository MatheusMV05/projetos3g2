import { useState } from 'react';

// Tipos
export interface Event {
  id: number;
  title: string;
  description: string;
  additionalInfo: string;
  imageUrl?: string;
}

export interface SectionContent {
  id: string;
  title: string;
  subtitle: string;
  buttonText: string;
}

// Storages locais (simulam banco de dados)
const SECTION_CONTENT_KEY = 'mock-section-content';
const EVENTS_KEY = 'mock-events';

// Dados mock iniciais
const INITIAL_SECTION_CONTENT: Record<string, SectionContent> = {
  'proximos-eventos': {
    id: 'proximos-eventos',
    title: 'Próximos eventos',
    subtitle: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
    buttonText: 'Ver todos'
  },
  // Adicionar outras seções conforme necessário
};

const INITIAL_EVENTS: Event[] = [
  {
    id: 1,
    title: 'Nome do evento 1',
    description: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
    additionalInfo: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.'
  },
  {
    id: 2,
    title: 'Nome do evento 2',
    description: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
    additionalInfo: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.'
  },
  {
    id: 3,
    title: 'Nome do evento 3',
    description: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.',
    additionalInfo: 'Lorem ipsum dolor sit amet consectetur adipiscing elit.'
  }
];

// Funções de inicialização
const initLocalStorage = () => {
  if (!localStorage.getItem(SECTION_CONTENT_KEY)) {
    localStorage.setItem(SECTION_CONTENT_KEY, JSON.stringify(INITIAL_SECTION_CONTENT));
  }
  
  if (!localStorage.getItem(EVENTS_KEY)) {
    localStorage.setItem(EVENTS_KEY, JSON.stringify(INITIAL_EVENTS));
  }
};

// Serviço de Mock
export const useMockService = () => {
  // Inicializar localStorage na primeira vez
  useState(() => {
    initLocalStorage();
  });

  // Funções para gerenciar seções
  const getSectionContent = async (identifier: string): Promise<SectionContent> => {
    const data = localStorage.getItem(SECTION_CONTENT_KEY);
    const sections = data ? JSON.parse(data) : INITIAL_SECTION_CONTENT;
    
    if (!sections[identifier]) {
      throw new Error(`Seção '${identifier}' não encontrada`);
    }
    
    // Simular atraso de rede
    await new Promise(resolve => setTimeout(resolve, 300));
    
    return sections[identifier];
  };

  const updateSectionContent = async (identifier: string, content: Partial<SectionContent>): Promise<SectionContent> => {
    const data = localStorage.getItem(SECTION_CONTENT_KEY);
    const sections = data ? JSON.parse(data) : INITIAL_SECTION_CONTENT;
    
    if (!sections[identifier]) {
      throw new Error(`Seção '${identifier}' não encontrada`);
    }
    
    // Atualizar conteúdo
    sections[identifier] = {
      ...sections[identifier],
      ...content,
      id: identifier // Garantir que o ID não seja alterado
    };
    
    // Salvar no localStorage
    localStorage.setItem(SECTION_CONTENT_KEY, JSON.stringify(sections));
    
    // Simular atraso de rede
    await new Promise(resolve => setTimeout(resolve, 300));
    
    return sections[identifier];
  };

  // Funções para gerenciar eventos
  const getAllEvents = async (): Promise<Event[]> => {
    const data = localStorage.getItem(EVENTS_KEY);
    const events = data ? JSON.parse(data) : INITIAL_EVENTS;
    
    // Simular atraso de rede
    await new Promise(resolve => setTimeout(resolve, 300));
    
    return events;
  };

  const getEventById = async (id: number): Promise<Event> => {
    const data = localStorage.getItem(EVENTS_KEY);
    const events = data ? JSON.parse(data) : INITIAL_EVENTS;
    
    const event = events.find((e: Event) => e.id === id);
    
    if (!event) {
      throw new Error(`Evento com ID ${id} não encontrado`);
    }
    
    // Simular atraso de rede
    await new Promise(resolve => setTimeout(resolve, 300));
    
    return event;
  };

  const createEvent = async (event: Omit<Event, 'id'>): Promise<Event> => {
    const data = localStorage.getItem(EVENTS_KEY);
    const events = data ? JSON.parse(data) : INITIAL_EVENTS;
    
    // Gerar ID único
    const maxId = Math.max(...events.map((e: Event) => e.id), 0);
    const newEvent = {
      ...event,
      id: maxId + 1
    };
    
    // Adicionar novo evento
    events.push(newEvent);
    
    // Salvar no localStorage
    localStorage.setItem(EVENTS_KEY, JSON.stringify(events));
    
    // Simular atraso de rede
    await new Promise(resolve => setTimeout(resolve, 300));
    
    return newEvent;
  };

  const updateEvent = async (id: number, event: Partial<Event>): Promise<Event> => {
    const data = localStorage.getItem(EVENTS_KEY);
    const events = data ? JSON.parse(data) : INITIAL_EVENTS;
    
    const index = events.findIndex((e: Event) => e.id === id);
    
    if (index === -1) {
      throw new Error(`Evento com ID ${id} não encontrado`);
    }
    
    // Atualizar evento
    events[index] = {
      ...events[index],
      ...event,
      id // Garantir que o ID não seja alterado
    };
    
    // Salvar no localStorage
    localStorage.setItem(EVENTS_KEY, JSON.stringify(events));
    
    // Simular atraso de rede
    await new Promise(resolve => setTimeout(resolve, 300));
    
    return events[index];
  };

  const deleteEvent = async (id: number): Promise<boolean> => {
    const data = localStorage.getItem(EVENTS_KEY);
    const events = data ? JSON.parse(data) : INITIAL_EVENTS;
    
    const index = events.findIndex((e: Event) => e.id === id);
    
    if (index === -1) {
      throw new Error(`Evento com ID ${id} não encontrado`);
    }
    
    // Remover evento
    events.splice(index, 1);
    
    // Salvar no localStorage
    localStorage.setItem(EVENTS_KEY, JSON.stringify(events));
    
    // Simular atraso de rede
    await new Promise(resolve => setTimeout(resolve, 300));
    
    return true;
  };

  // Função de mock para upload de imagem
  const uploadImage = async (file: File): Promise<string> => {
    // Criar URL de dados para simular URL de imagem
    return new Promise((resolve) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        // Simular atraso de rede
        setTimeout(() => {
          // Retornar URL de dados como se fosse URL de imagem do servidor
          resolve(reader.result as string);
        }, 1000);
      };
      reader.readAsDataURL(file);
    });
  };

  return {
    // API de seções
    getSectionContent,
    updateSectionContent,
    
    // API de eventos
    getAllEvents,
    getEventById,
    createEvent,
    updateEvent,
    deleteEvent,
    
    // API de upload
    uploadImage
  };
};

// Exportar hooks de serviço mock que podem ser usados diretamente nos componentes
export default useMockService;