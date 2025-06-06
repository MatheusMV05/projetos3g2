-- init-database.sql
-- Script para inicialização do banco de dados
-- Arquivo para colocar em Backend/src/main/resources/db/migration/V001__init_database.sql

-- Criar tabela de perfis
CREATE TABLE IF NOT EXISTS perfis (
                                      id BIGSERIAL PRIMARY KEY,
                                      nome VARCHAR(100) NOT NULL UNIQUE,
                                      descricao VARCHAR(255),
                                      data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (
                                        id BIGSERIAL PRIMARY KEY,
                                        email VARCHAR(100) NOT NULL UNIQUE,
                                        senha VARCHAR(255) NOT NULL,
                                        nome VARCHAR(100) NOT NULL,
                                        ativo BOOLEAN DEFAULT TRUE,
                                        mfa_ativado BOOLEAN DEFAULT FALSE,
                                        mfa_secret VARCHAR(255),
                                        ultimo_login TIMESTAMP,
                                        data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de relacionamento usuário-perfil
CREATE TABLE IF NOT EXISTS usuario_perfis (
                                              usuario_id BIGINT NOT NULL,
                                              perfil_id BIGINT NOT NULL,
                                              PRIMARY KEY (usuario_id, perfil_id),
                                              FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
                                              FOREIGN KEY (perfil_id) REFERENCES perfis(id) ON DELETE CASCADE
);

-- Criar tabela de páginas
CREATE TABLE IF NOT EXISTS pagina (
                                      id BIGSERIAL PRIMARY KEY,
                                      titulo VARCHAR(255) NOT NULL,
                                      slug VARCHAR(255) NOT NULL UNIQUE,
                                      conteudo TEXT,
                                      data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de informações institucionais
CREATE TABLE IF NOT EXISTS informacoes_institucionais (
                                                          id BIGSERIAL PRIMARY KEY,
                                                          chave VARCHAR(255) NOT NULL UNIQUE,
                                                          valor TEXT NOT NULL,
                                                          tipo VARCHAR(50) NOT NULL,
                                                          descricao VARCHAR(255),
                                                          ativo BOOLEAN DEFAULT TRUE,
                                                          data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                          data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de métricas de impacto
CREATE TABLE IF NOT EXISTS metricas_impacto (
                                                id BIGSERIAL PRIMARY KEY,
                                                titulo VARCHAR(255) NOT NULL,
                                                valor DECIMAL(15,2) NOT NULL,
                                                unidade VARCHAR(50) NOT NULL,
                                                descricao TEXT,
                                                categoria VARCHAR(100) NOT NULL,
                                                ano INTEGER NOT NULL,
                                                tipo_iniciativa VARCHAR(100),
                                                icone VARCHAR(50),
                                                ordem INTEGER DEFAULT 0,
                                                ativo BOOLEAN DEFAULT TRUE,
                                                data_inclusao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de depoimentos
CREATE TABLE IF NOT EXISTS depoimentos (
                                           id BIGSERIAL PRIMARY KEY,
                                           nome VARCHAR(100) NOT NULL,
                                           cargo VARCHAR(100) NOT NULL,
                                           organizacao VARCHAR(200) NOT NULL,
                                           texto TEXT NOT NULL,
                                           foto_url VARCHAR(500),
                                           iniciativa_relacionada VARCHAR(255),
                                           ano INTEGER NOT NULL,
                                           ordem INTEGER DEFAULT 0,
                                           destaque BOOLEAN DEFAULT FALSE,
                                           ativo BOOLEAN DEFAULT TRUE,
                                           data_inclusao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                           data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de logs de auditoria
CREATE TABLE IF NOT EXISTS logs_auditoria (
                                              id BIGSERIAL PRIMARY KEY,
                                              usuario_id BIGINT,
                                              acao VARCHAR(100) NOT NULL,
                                              entidade VARCHAR(100) NOT NULL,
                                              entidade_id BIGINT,
                                              detalhes VARCHAR(1000),
                                              ip VARCHAR(45) NOT NULL,
                                              data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                              FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Criar tabela de eventos analytics
CREATE TABLE IF NOT EXISTS eventos_analytics (
                                                 id BIGSERIAL PRIMARY KEY,
                                                 tipo VARCHAR(50) NOT NULL,
                                                 categoria VARCHAR(100) NOT NULL,
                                                 acao VARCHAR(100),
                                                 rotulo VARCHAR(255),
                                                 valor INTEGER,
                                                 ip_usuario VARCHAR(45) NOT NULL,
                                                 user_agent TEXT,
                                                 pagina_referencia VARCHAR(500),
                                                 pagina_atual VARCHAR(500),
                                                 usuario_id BIGINT,
                                                 data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de mensagens de contato
CREATE TABLE IF NOT EXISTS mensagens_contato (
                                                 id BIGSERIAL PRIMARY KEY,
                                                 nome VARCHAR(100) NOT NULL,
                                                 email VARCHAR(100) NOT NULL,
                                                 assunto VARCHAR(200) NOT NULL,
                                                 mensagem TEXT NOT NULL,
                                                 lida BOOLEAN DEFAULT FALSE,
                                                 respondida BOOLEAN DEFAULT FALSE,
                                                 resposta TEXT,
                                                 usuario_resposta_id BIGINT,
                                                 data_resposta TIMESTAMP,
                                                 data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de assinantes da newsletter
CREATE TABLE IF NOT EXISTS assinantes_newsletter (
                                                     id BIGSERIAL PRIMARY KEY,
                                                     email VARCHAR(100) NOT NULL UNIQUE,
                                                     ativo BOOLEAN DEFAULT TRUE,
                                                     nome VARCHAR(100),
                                                     data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                     data_desativacao TIMESTAMP
);

-- Criar tabela de newsletters
CREATE TABLE IF NOT EXISTS newsletters (
                                           id BIGSERIAL PRIMARY KEY,
                                           assunto VARCHAR(200) NOT NULL,
                                           conteudo TEXT NOT NULL,
                                           enviada BOOLEAN DEFAULT FALSE,
                                           data_envio TIMESTAMP,
                                           quantidade_destinatarios INTEGER,
                                           usuario_id BIGINT,
                                           data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar tabela de notificações
CREATE TABLE IF NOT EXISTS notificacoes (
                                            id BIGSERIAL PRIMARY KEY,
                                            titulo VARCHAR(255) NOT NULL,
                                            mensagem TEXT NOT NULL,
                                            tipo VARCHAR(50) NOT NULL,
                                            acao VARCHAR(500),
                                            lida BOOLEAN DEFAULT FALSE,
                                            usuario_id BIGINT,
                                            data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            data_leitura TIMESTAMP,
                                            FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Inserir perfis padrão
INSERT INTO perfis (nome, descricao) VALUES
                                         ('ADMIN', 'Administrador do sistema'),
                                         ('USER', 'Usuário comum')
ON CONFLICT (nome) DO NOTHING;

-- Criar índices para performance
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);
CREATE INDEX IF NOT EXISTS idx_pagina_slug ON pagina(slug);
CREATE INDEX IF NOT EXISTS idx_informacoes_chave ON informacoes_institucionais(chave);
CREATE INDEX IF NOT EXISTS idx_metricas_categoria ON metricas_impacto(categoria);
CREATE INDEX IF NOT EXISTS idx_metricas_ano ON metricas_impacto(ano);
CREATE INDEX IF NOT EXISTS idx_depoimentos_ano ON depoimentos(ano);
CREATE INDEX IF NOT EXISTS idx_logs_data_hora ON logs_auditoria(data_hora);
CREATE INDEX IF NOT EXISTS idx_eventos_data_hora ON eventos_analytics(data_hora);
CREATE INDEX IF NOT EXISTS idx_mensagens_data_criacao ON mensagens_contato(data_criacao);
CREATE INDEX IF NOT EXISTS idx_notificacoes_usuario_lida ON notificacoes(usuario_id, lida);

-- Criar função para atualizar data_atualizacao automaticamente
CREATE OR REPLACE FUNCTION update_modified_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.data_atualizacao = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Criar triggers para atualização automática
DROP TRIGGER IF EXISTS update_usuarios_modtime ON usuarios;
CREATE TRIGGER update_usuarios_modtime BEFORE UPDATE ON usuarios FOR EACH ROW EXECUTE FUNCTION update_modified_column();

DROP TRIGGER IF EXISTS update_pagina_modtime ON pagina;
CREATE TRIGGER update_pagina_modtime BEFORE UPDATE ON pagina FOR EACH ROW EXECUTE FUNCTION update_modified_column();

DROP TRIGGER IF EXISTS update_informacoes_modtime ON informacoes_institucionais;
CREATE TRIGGER update_informacoes_modtime BEFORE UPDATE ON informacoes_institucionais FOR EACH ROW EXECUTE FUNCTION update_modified_column();

DROP TRIGGER IF EXISTS update_metricas_modtime ON metricas_impacto;
CREATE TRIGGER update_metricas_modtime BEFORE UPDATE ON metricas_impacto FOR EACH ROW EXECUTE FUNCTION update_modified_column();

DROP TRIGGER IF EXISTS update_depoimentos_modtime ON depoimentos;
CREATE TRIGGER update_depoimentos_modtime BEFORE UPDATE ON depoimentos FOR EACH ROW EXECUTE FUNCTION update_modified_column();