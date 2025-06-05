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
  onSwitchToLogin: () => void; // ✅ nova prop
}

const Register: React.FC<RegisterProps> = ({ isOpen, onClose }) => {
  const [formData, setFormData] = useState<RegisterFormData>({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    photo: null,
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, files } = e.target;

    setFormData({
      ...formData,
      [name]: type === 'file' ? (files ? files[0] : null) : value,
    });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      alert('As senhas não coincidem!');
      return;
    }

    console.log('Enviando dados:', formData);
  };

  if (!isOpen) return null;

  return (
    <div className="modal-background" onClick={onClose}>
      <div className="form-container" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}>×</button>
        <h2>Cadastro de Usuário</h2>
        <form onSubmit={handleSubmit} className="form">
          <div className="form-group">
            <label htmlFor="photo">Foto</label>
            <input type="file" id="photo" name="photo" accept="image/*" onChange={handleChange} />
          </div>
          <div className="form-group">
            <label htmlFor="name">Nome</label>
            <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />
          </div>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} required />
          </div>
          <div className="form-group">
            <label htmlFor="password">Senha</label>
            <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />
          </div>
          <div className="form-group">
            <label htmlFor="confirmPassword">Confirmar Senha</label>
            <input type="password" id="confirmPassword" name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} required />
          </div>
          <button type="submit" className="submit-btn">Cadastrar</button>

         
        </form>
      </div>
    </div>
  );
};

export default Register;
