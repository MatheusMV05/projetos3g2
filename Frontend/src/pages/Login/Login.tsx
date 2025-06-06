import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthService } from '../../services/authService';
import './Login.css';

interface LoginFormData {
  email: string;
  password: string;
}

interface LoginProps {
  isOpen: boolean;
  onClose: () => void;
  onLoginSuccess?: () => void; // Callback opcional para quando o login for bem-sucedido
}

const Login: React.FC<LoginProps> = ({ isOpen, onClose, onLoginSuccess }) => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState<LoginFormData>({
    email: '',
    password: '',
  });

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string>('');
  const [requiresMfa, setRequiresMfa] = useState(false);
  const [mfaCode, setMfaCode] = useState('');
  const [mfaSecret, setMfaSecret] = useState<string>('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));

    // Limpa o erro quando o usuário começar a digitar
    if (error) {
      setError('');
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);

    try {
      const response = await AuthService.login(formData.email, formData.password);

      if (response.requiresMfa) {
        // Se MFA for necessário, mostra o campo para código MFA
        setRequiresMfa(true);
        setMfaSecret(response.mfaSecret || '');
        setIsLoading(false);
        return;
      }

      // Login bem-sucedido
      console.log('✅ Login realizado com sucesso!');

      // Callback opcional para notificar componente pai
      if (onLoginSuccess) {
        onLoginSuccess();
      }

      // Fecha o modal e reseta o formulário
      handleClose();

      // Redireciona para a página de conteúdo
      navigate('/conteudo');

    } catch (err: any) {
      console.error('❌ Erro no login:', err);

      // Trata diferentes tipos de erro
      if (err.response?.status === 401) {
        setError('Email ou senha incorretos. Tente novamente.');
      } else if (err.response?.status === 400) {
        setError('Dados inválidos. Verifique email e senha.');
      } else if (err.response?.status === 500) {
        setError('Erro interno do servidor. Tente novamente mais tarde.');
      } else if (err.code === 'NETWORK_ERROR' || !err.response) {
        setError('Erro de conexão. Verifique sua internet e tente novamente.');
      } else {
        setError(err.response?.data?.message || 'Erro inesperado. Tente novamente.');
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleMfaSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);

    try {
      await AuthService.verifyMfa(formData.email, mfaCode);

      console.log('✅ MFA verificado com sucesso!');

      if (onLoginSuccess) {
        onLoginSuccess();
      }

      handleClose();

      // Redireciona para a página de conteúdo
      navigate('/conteudo');

    } catch (err: any) {
      console.error('❌ Erro na verificação MFA:', err);
      setError('Código MFA inválido. Tente novamente.');
    } finally {
      setIsLoading(false);
    }
  };

  const handleClose = () => {
    // Reset todos os estados
    setFormData({ email: '', password: '' });
    setError('');
    setRequiresMfa(false);
    setMfaCode('');
    setMfaSecret('');
    setIsLoading(false);
    onClose();
  };

  const fillDemoCredentials = () => {
    setFormData({
      email: 'admin@brasfi.com.br',
      password: '123456'
    });
  };

  if (!isOpen) return null;

  return (
      <div className="modal-background" onClick={handleClose}>
        <div className="form-container" onClick={(e) => e.stopPropagation()}>
          <button className="close-btn" onClick={handleClose} disabled={isLoading}>
            ×
          </button>

          <h2>{requiresMfa ? 'Verificação MFA' : 'Login'}</h2>

          {/* Botão para preencher credenciais de desenvolvimento */}
          {!requiresMfa && process.env.NODE_ENV === 'development' && (
              <button
                  type="button"
                  onClick={fillDemoCredentials}
                  style={{
                    background: '#f0f8ff',
                    border: '1px solid #007bff',
                    padding: '0.3rem 0.8rem',
                    fontSize: '0.8rem',
                    marginBottom: '1rem',
                    borderRadius: '4px',
                    cursor: 'pointer'
                  }}
              >
                🔧 Preencher credenciais de desenvolvimento
              </button>
          )}

          {/* Mensagem de erro */}
          {error && (
              <div className="error-message" style={{
                color: '#d93025',
                backgroundColor: '#ffeaa7',
                padding: '0.8rem',
                marginBottom: '1rem',
                borderRadius: '4px',
                border: '1px solid #fab1a0'
              }}>
                {error}
              </div>
          )}

          {!requiresMfa ? (
              /* Formulário de Login */
              <form onSubmit={handleSubmit} className="form">
                <div className="form-group">
                  <label htmlFor="email">Email</label>
                  <input
                      type="email"
                      id="email"
                      name="email"
                      placeholder="Digite seu email"
                      value={formData.email}
                      onChange={handleChange}
                      required
                      disabled={isLoading}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="password">Senha</label>
                  <input
                      type="password"
                      id="password"
                      name="password"
                      placeholder="Digite sua senha"
                      value={formData.password}
                      onChange={handleChange}
                      required
                      disabled={isLoading}
                  />
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
                  {isLoading ? 'Entrando...' : 'Entrar'}
                </button>
              </form>
          ) : (
              /* Formulário de MFA */
              <form onSubmit={handleMfaSubmit} className="form">
                <p style={{ marginBottom: '1rem', fontSize: '0.9rem', color: '#666' }}>
                  Digite o código de 6 dígitos do seu aplicativo autenticador:
                </p>

                {mfaSecret && (
                    <div style={{
                      background: '#f0f8ff',
                      padding: '0.8rem',
                      marginBottom: '1rem',
                      borderRadius: '4px',
                      fontSize: '0.8rem'
                    }}>
                      <strong>Segredo MFA:</strong> {mfaSecret}
                    </div>
                )}

                <div className="form-group">
                  <label htmlFor="mfaCode">Código MFA</label>
                  <input
                      type="text"
                      id="mfaCode"
                      name="mfaCode"
                      placeholder="123456"
                      value={mfaCode}
                      onChange={(e) => setMfaCode(e.target.value)}
                      required
                      disabled={isLoading}
                      maxLength={6}
                      pattern="[0-9]{6}"
                  />
                </div>

                <div style={{ display: 'flex', gap: '0.5rem' }}>
                  <button
                      type="button"
                      onClick={() => setRequiresMfa(false)}
                      disabled={isLoading}
                      style={{
                        flex: 1,
                        padding: '0.8rem',
                        background: '#f8f9fa',
                        border: '1px solid #dee2e6',
                        borderRadius: '6px',
                        cursor: 'pointer'
                      }}
                  >
                    Voltar
                  </button>

                  <button
                      type="submit"
                      className="submit-btn"
                      disabled={isLoading}
                      style={{
                        flex: 2,
                        opacity: isLoading ? 0.6 : 1,
                        cursor: isLoading ? 'not-allowed' : 'pointer'
                      }}
                  >
                    {isLoading ? 'Verificando...' : 'Verificar'}
                  </button>
                </div>
              </form>
          )}

          {/* Informações para desenvolvimento */}
          {process.env.NODE_ENV === 'development' && !requiresMfa && (
              <div style={{
                marginTop: '1rem',
                fontSize: '0.8rem',
                color: '#666',
                borderTop: '1px solid #eee',
                paddingTop: '1rem'
              }}>
                <strong>Credenciais de desenvolvimento:</strong><br/>
                📧 admin@brasfi.com.br<br/>
                🔑 123456
              </div>
          )}
        </div>
      </div>
  );
};

export default Login;