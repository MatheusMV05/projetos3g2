import './Equipe.css';

const Equipe = () => {
  return (
    <main className="equipe">
      <h1>Equipe</h1>
      <p>
        Convide membros da equipe para o seu workspace e dê a eles acesso aos seus sites e projetos.{' '}
        <a href="#">Saiba mais</a>
      </p>

      <div className="equipe-actions">
        <button className="btn-secondary">Gerenciar funções</button>
        <button className="btn-primary">Convidar membros da equipe</button>
      </div>

      <div className="equipe-filtros">
        <select>
          <option>Todos</option>
        </select>
        <select>
          <option>Função</option>
        </select>
        <input type="text" placeholder="Buscar email ou nome" />
      </div>

      <table className="equipe-tabela">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Função</th>
            <th>Data</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <div className="equipe-user">
                <img src="/user.png" alt="Avatar" className="user-avatar" />
                <div>
                  <strong>JOEL LUIZ LIMA DIONIZIO</strong>
                  <div>jlld@cesar.school</div>
                </div>
              </div>
            </td>
            <td>Proprietário</td>
            <td>Entrou em 13 de mai. de 2025</td>
          </tr>
        </tbody>
      </table>
    </main>
  );
};

export default Equipe;