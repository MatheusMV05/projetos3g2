import React, { useState } from 'react';

interface DevLoginHelperProps {
    onLogin?: (token: string) => void;
}

const DevLoginHelper: React.FC<DevLoginHelperProps> = ({ onLogin }) => {
    const [isLoading, setIsLoading] = useState(false);
    const [message, setMessage] = useState<string>('');

    const handleQuickLogin = async () => {
        setIsLoading(true);
        setMessage('');

        try {
            console.log('🔐 Fazendo login automático com usuário padrão...');

            const response = await fetch('http://localhost:8080/api/auth/authenticate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: 'admin@brasfi.com.br',
                    senha: '123456'
                })
            });

            console.log('📡 Status da resposta:', response.status);

            if (response.ok) {
                const data = await response.json();
                console.log('✅ Login bem-sucedido:', data);

                if (data.token) {
                    localStorage.setItem('token', data.token);
                    setMessage('✅ Login realizado com sucesso!');

                    if (onLogin) {
                        onLogin(data.token);
                    }

                    // Opcional: recarregar a página para atualizar o estado global
                    setTimeout(() => {
                        window.location.reload();
                    }, 1000);
                } else {
                    setMessage('❌ Token não encontrado na resposta');
                }
            } else {
                const errorData = await response.json();
                console.error('❌ Erro de login:', errorData);
                setMessage(`❌ Erro: ${errorData.message || response.statusText}`);
            }
        } catch (error) {
            console.error('❌ Erro de conexão:', error);
            setMessage('❌ Erro ao conectar com o servidor');
        } finally {
            setIsLoading(false);
        }
    };

    const handleTestConnection = async () => {
        setIsLoading(true);
        setMessage('');

        try {
            console.log('🔍 Testando conexão com o servidor...');

            const response = await fetch('http://localhost:8080/api/dev/status');
            console.log('📡 Status da resposta:', response.status);

            if (response.ok) {
                const data = await response.json();
                console.log('✅ Servidor conectado:', data);
                setMessage('✅ Servidor funcionando corretamente!');
            } else {
                setMessage(`❌ Servidor retornou: ${response.status} ${response.statusText}`);
            }
        } catch (error) {
            console.error('❌ Erro de conexão:', error);
            setMessage('❌ Não foi possível conectar ao servidor');
        } finally {
            setIsLoading(false);
        }
    };

    const handleGetLoginInfo = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/dev/login-info');
            if (response.ok) {
                const data = await response.json();
                console.log('📋 Informações de login:', data);
                alert(`Credenciais padrão:\nEmail: ${data.adminUser.email}\nSenha: ${data.adminUser.password}`);
            }
        } catch (error) {
            console.error('Erro ao obter informações:', error);
        }
    };

    const currentToken = localStorage.getItem('token');

    return (
        <div style={{
            position: 'fixed',
            top: '20px',
            right: '20px',
            backgroundColor: '#f0f8ff',
            border: '2px solid #007bff',
            borderRadius: '8px',
            padding: '15px',
            minWidth: '300px',
            boxShadow: '0 4px 6px rgba(0,0,0,0.1)',
            zIndex: 9999,
            fontSize: '14px'
        }}>
            <h4 style={{ margin: '0 0 15px 0', color: '#007bff' }}>
                🛠️ Dev Helper
            </h4>

            <div style={{ marginBottom: '10px' }}>
                <strong>Status:</strong> {currentToken ? '✅ Logado' : '❌ Não logado'}
            </div>

            {message && (
                <div style={{
                    marginBottom: '15px',
                    padding: '8px',
                    backgroundColor: message.includes('✅') ? '#d4edda' : '#f8d7da',
                    border: '1px solid ' + (message.includes('✅') ? '#c3e6cb' : '#f5c6cb'),
                    borderRadius: '4px',
                    fontSize: '12px'
                }}>
                    {message}
                </div>
            )}

            <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
                <button
                    onClick={handleTestConnection}
                    disabled={isLoading}
                    style={{
                        padding: '8px 12px',
                        backgroundColor: '#28a745',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: isLoading ? 'not-allowed' : 'pointer',
                        opacity: isLoading ? 0.6 : 1
                    }}
                >
                    {isLoading ? '⏳ Testando...' : '🔍 Testar Servidor'}
                </button>

                <button
                    onClick={handleQuickLogin}
                    disabled={isLoading}
                    style={{
                        padding: '8px 12px',
                        backgroundColor: '#007bff',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: isLoading ? 'not-allowed' : 'pointer',
                        opacity: isLoading ? 0.6 : 1
                    }}
                >
                    {isLoading ? '⏳ Entrando...' : '🔐 Login Automático'}
                </button>

                <button
                    onClick={handleGetLoginInfo}
                    style={{
                        padding: '8px 12px',
                        backgroundColor: '#6c757d',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer'
                    }}
                >
                    📋 Ver Credenciais
                </button>

                {currentToken && (
                    <button
                        onClick={() => {
                            localStorage.removeItem('token');
                            setMessage('🚪 Logout realizado');
                            setTimeout(() => window.location.reload(), 500);
                        }}
                        style={{
                            padding: '8px 12px',
                            backgroundColor: '#dc3545',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px',
                            cursor: 'pointer'
                        }}
                    >
                        🚪 Logout
                    </button>
                )}
            </div>

            <div style={{
                marginTop: '15px',
                fontSize: '11px',
                color: '#666',
                borderTop: '1px solid #ddd',
                paddingTop: '10px'
            }}>
                <strong>Credenciais padrão:</strong><br/>
                📧 admin@brasfi.com.br<br/>
                🔑 123456
            </div>
        </div>
    );
};

export default DevLoginHelper;