import React, { useState } from 'react';
import './Login.css';

interface LoginFormData {
  email: string;
  password: string;
}

interface LoginProps {
  isOpen: boolean;
  onClose: () => void;
}

const Login: React.FC<LoginProps> = ({ isOpen, onClose }) => {
  const [formData, setFormData] = useState<LoginFormData>({
    email: '',
    password: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Login enviado:', formData);
    // Aqui você pode chamar a função de autenticação
  };

  if (!isOpen) return null;

  return (
    <div className="modal-background" onClick={onClose}>
      <div className="form-container" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}>×</button>
        <h2>Login</h2>
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
            />
          </div>

          <button type="submit" className="submit-btn">
            Entrar
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
