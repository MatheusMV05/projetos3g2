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
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      alert('As senhas não coincidem!');
      return;
    }

    const form = new FormData();
    form.append('nome', formData.name);
    form.append('email', formData.email);
    form.append('senha', formData.password);
    form.append('perfil', 'ADMIN'); // todos os cadastros são admins
    if (formData.photo) {
      form.append('foto', formData.photo);
    }

    try {
      const response = await fetch('http://localhost:8080/api/usuarios', {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
        body: form,
      });

      if (response.ok) {
        alert('Usuário cadastrado com sucesso!');
        setFormData({
          name: '',
          email: '',
          password: '',
          confirmPassword: '',
          photo: null,
        });
        onClose();
      } else {
        const data = await response.json();
        alert(data.message || 'Erro ao cadastrar usuário.');
      }
    } catch (error) {
      alert('Erro ao conectar com o servidor.');
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-background" onClick={onClose}>
      <div className="form-container" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}>
          ×
        </button>
        <h2>Cadastro de Usuário</h2>
        <form onSubmit={handleSubmit} className="form">
          <div className="form-group">
            <label htmlFor="photo">Foto</label>
            <input
              type="file"
              id="photo"
              name="photo"
              accept="image/*"
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="name">Nome</label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
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
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="confirmPassword">Confirmar Senha</label>
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              required
            />
          </div>
          <button type="submit" className="submit-btn">
            Cadastrar
          </button>
        </form>
      </div>
    </div>
  );
};

export default Register;
