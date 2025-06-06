import React, { useState } from 'react';
import '../Login/Login.css';

interface RegisterFormData {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
  photo: File | null;
}

interface RegisterProps {
  isOpen: boolean;
  onClose: () => void;
}

const Register: React.FC<RegisterProps> = ({ isOpen, onClose }) => {
  const [formData, setFormData] = useState<RegisterFormData>({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    photo: null,
  });

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string>('');

  const handleChange = (
      e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const target = e.target as HTMLInputElement;
    const { name, value, type } = target;
    const files = (target as HTMLInputElement).files;

    setFormData((prev) => ({
      ...prev,
      [name]: type === 'file' ? (files ? files[0] : null) : value,
    }));

    // Limpar erro quando usuário começar a digitar
    if (error) {
      setError('');
    }
  };

  const validateForm = (): boolean => {
    if (!formData.name.trim()) {
      setError('Nome é obrigatório');
      return false;
    }

    if (!formData.email.trim()) {
      setError('Email é obrigatório');
      return false;
    }

    if (!formData.password) {
      setError('Senha é obrigatória');
      return false;
    }

    if (formData.password.length < 6) {
      setError('Senha deve ter no mínimo 6 caracteres');
      return false;
    }

    if (formData.password !== formData.confirmPassword) {
      setError('As senhas não coincidem!');
      return false;
    }

    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (!validateForm()) {
      return;
    }

    setIsLoading(true);

    const form = new FormData();
    form.append('nome', formData.name.trim());
    form.append('email', formData.email.trim());
    form.append('senha', formData.password);

    // Só adicionar foto se ela existir
    if (formData.photo) {
      form.append('foto', formData.photo);
    }

    try {
      const token = localStorage.getItem('token');

      if (!token) {
        setError('Token de autenticação não encontrado. Faça login novamente.');
        console.log('❌ Token não encontrado. Use as credenciais: admin@brasfi.com.br / 123456');
        setIsLoading(false);
        return;
      }

      console.log('🚀 Enviando requisição para cadastro de usuário...');
      console.log('📧 Email:', formData.email.trim());
      console.log('👤 Nome:', formData.name.trim());
      console.log('🗃️ Foto:', formData.photo ? 'Sim' : 'Não');

      const response = await fetch('http://localhost:8080/api/usuarios', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        body: form,
      });

      console.log('📡 Status da resposta:', response.status);
      console.log('📡 Headers da resposta:', Object.fromEntries(response.headers.entries()));

      if (response.ok) {
        const result = await response.json();
        console.log('✅ Sucesso:', result);

        alert('✅ Usuário cadastrado com sucesso!');

        // Limpar formulário
        setFormData({
          name: '',
          email: '',
          password: '',
          confirmPassword: '',
          photo: null,
        });

        onClose();
      } else {
        const errorData = await response.json();
        console.error('❌ Erro da API:', errorData);

        let errorMessage = errorData.message || `Erro ${response.status}: ${response.statusText}`;

        // Mensagens específicas para erros comuns
        if (response.status === 401) {
          errorMessage = 'Token inválido ou expirado. Faça login novamente com: admin@brasfi.com.br / 123456';
        } else if (response.status === 403) {
          errorMessage = 'Acesso negado. Certifique-se de estar logado como administrador.';
        } else if (response.status === 400 && errorData.errors) {
          const errorDetails = Object.entries(errorData.errors)
              .map(([field, message]) => `${field}: ${message}`)
              .join('\n');
          errorMessage = `Dados inválidos:\n${errorDetails}`;
        }

        setError(errorMessage);
      }
    } catch (error) {
      console.error('❌ Erro de conexão:', error);
      setError('Erro ao conectar com o servidor. Verifique se o backend está rodando em http://localhost:8080');
    } finally {
      setIsLoading(false);
    }
  };

  const resetForm = () => {
    setFormData({
      name: '',
      email: '',
      password: '',
      confirmPassword: '',
      photo: null,
    });
    setError('');
  };

  const handleClose = () => {
    resetForm();
    onClose();
  };

  if (!isOpen) return null;

  return (
      <div className="modal-background" onClick={handleClose}>
        <div className="form-container" onClick={(e) => e.stopPropagation()}>
          <button className="close-btn" onClick={handleClose} disabled={isLoading}>
            ×
          </button>
          <h2>Cadastro de Usuário Administrador</h2>

          {error && (
              <div className="error-message" style={{
                color: 'red',
                marginBottom: '15px',
                padding: '10px',
                backgroundColor: '#fee',
                border: '1px solid #fcc',
                borderRadius: '4px'
              }}>
                {error}
              </div>
          )}

          <form onSubmit={handleSubmit} className="form">
            <div className="form-group">
              <label htmlFor="name">Nome *</label>
              <input
                  type="text"
                  id="name"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  required
                  disabled={isLoading}
                  placeholder="Digite o nome completo"
              />
            </div>

            <div className="form-group">
              <label htmlFor="email">Email *</label>
              <input
                  type="email"
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                  disabled={isLoading}
                  placeholder="Digite o email"
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">Senha *</label>
              <input
                  type="password"
                  id="password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                  disabled={isLoading}
                  placeholder="Mínimo 6 caracteres"
                  minLength={6}
              />
            </div>

            <div className="form-group">
              <label htmlFor="confirmPassword">Confirmar Senha *</label>
              <input
                  type="password"
                  id="confirmPassword"
                  name="confirmPassword"
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  required
                  disabled={isLoading}
                  placeholder="Confirme a senha"
              />
            </div>

            <div className="form-group">
              <label htmlFor="photo">Foto (opcional)</label>
              <input
                  type="file"
                  id="photo"
                  name="photo"
                  accept="image/*"
                  onChange={handleChange}
                  disabled={isLoading}
              />
              <small style={{ color: '#666', fontSize: '12px' }}>
                Formatos aceitos: JPG, PNG, GIF
              </small>
            </div>

            <button
                type="submit"
                className="submit-btn"
                disabled={isLoading}
                style={{
                  opacity: isLoading ? 0.6 : 1,
                  cursor: isLoading ? 'not-allowed' : 'pointer'
                }}
            >
              {isLoading ? 'Cadastrando...' : 'Cadastrar'}
            </button>
          </form>
        </div>
      </div>
  );
};

export default Register;