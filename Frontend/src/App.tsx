import { useState } from 'react';
import './App.css';
import ApiTest from './components/ApiTest';

function App() {
  const [showTest, setShowTest] = useState(false);

  return (
    <div className="app-container">
      <header>
        <h1>Site Institucional</h1>
        <p>Projeto de gerenciamento de páginas institucionais</p>
      </header>

      <main>
        <button 
          onClick={() => setShowTest(!showTest)}
          className="toggle-button"
        >
          {showTest ? 'Esconder Teste de API' : 'Mostrar Teste de API'}
        </button>

        {showTest && <ApiTest />}
      </main>

      <footer>
        <p>© 2025 Site Institucional</p>
      </footer>
    </div>
  );
}

export default App;