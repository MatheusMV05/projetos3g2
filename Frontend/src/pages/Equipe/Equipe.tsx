import React, { useState, useEffect } from 'react';
import { EquipeService, Usuario } from '../../services/equipeService';
import Register from '../Register/Register';
import './Equipe.css';

const Equipe: React.FC = () => {
    const [membros, setMembros] = useState<Usuario[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isRegisterOpen, setRegisterOpen] = useState(false);

    const fetchMembros = async () => {
        setIsLoading(true);
        setError(null);
        try {
            const data = await EquipeService.listarMembros();
            setMembros(data);
        } catch (err: any) {
            console.error("Erro ao buscar membros da equipe:", err);
            setError("Falha ao carregar a equipe. Verifique se você está logado como administrador.");
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchMembros();
    }, []);

    const handleOpenRegister = () => setRegisterOpen(true);
    const handleCloseRegister = () => {
        setRegisterOpen(false);
        fetchMembros();
    };

    const handleDelete = async (id: number, nome: string) => {
        if (window.confirm(`Tem certeza que deseja excluir o usuário "${nome}"?`)) {
            try {
                await EquipeService.excluirMembro(id);
                setMembros(membros.filter(membro => membro.id !== id));
                alert("Usuário excluído com sucesso.");
            } catch (err) {
                console.error("Erro ao excluir membro:", err);
                alert("Falha ao excluir o membro. Tente novamente.");
            }
        }
    };

    const formatarData = (dataString: string | null) => {
        if (!dataString) return 'N/A';
        return new Date(dataString).toLocaleDateString('pt-BR', {
            day: '2-digit',
            month: 'short',
            year: 'numeric'
        });
    };

    return (
        <>
            <main className="equipe">
                <h1>Equipe</h1>
                <p>
                    Convide e gerencie os membros da equipe que têm acesso ao seu workspace.
                </p>

                <div className="equipe-actions">
                    <button className="btn-secondary" disabled>Gerenciar funções</button>
                    <button className="btn-primary" onClick={handleOpenRegister}>Convidar membro da equipe</button>
                </div>

                {isLoading && <p>Carregando equipe...</p>}
                {error && <p style={{ color: 'red' }}>{error}</p>}

                {!isLoading && !error && (
                    <table className="equipe-tabela">
                        <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Função</th>
                            <th>Data de Criação</th>
                            <th>Ações</th>
                        </tr>
                        </thead>
                        <tbody>
                        {membros.length > 0 ? (
                            membros.map(membro => (
                                <tr key={membro.id}>
                                    <td>
                                        <div className="equipe-user">
                                            <img src="/user.png" alt="Avatar" className="user-avatar" />
                                            <div>
                                                <strong>{membro.nome}</strong>
                                                <div>{membro.email}</div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>{membro.perfis.join(', ')}</td>
                                    <td>{formatarData(membro.dataCriacao)}</td>
                                    <td>
                                        <button onClick={() => handleDelete(membro.id, membro.nome)} style={{color: 'red', background: 'none', border: 'none', cursor: 'pointer'}}>
                                            Excluir
                                        </button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={4} style={{ textAlign: 'center' }}>Nenhum membro na equipe.</td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                )}
            </main>

            <Register
                isOpen={isRegisterOpen}
                onClose={handleCloseRegister}
            />
        </>
    );
};

export default Equipe;