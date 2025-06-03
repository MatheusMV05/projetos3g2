-- Tabela de categorias
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de tags
CREATE TABLE tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    usage_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela principal de publicações
CREATE TABLE publications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    slug VARCHAR(500) NOT NULL UNIQUE,
    summary TEXT,
    content LONGTEXT,
    type ENUM('ARTICLE', 'RESEARCH', 'NEWS') NOT NULL,
    status ENUM('DRAFT', 'PUBLISHED', 'ARCHIVED', 'SCHEDULED') DEFAULT 'DRAFT',
    author_id BIGINT,
    category_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMP NULL,
    scheduled_at TIMESTAMP NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    view_count INT DEFAULT 0,
    featured BOOLEAN DEFAULT FALSE,
    search_keywords TEXT,
    meta_title VARCHAR(255),
    meta_description TEXT,

    CONSTRAINT fk_publication_category FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT fk_publication_author FOREIGN KEY (author_id) REFERENCES users(id),

    INDEX idx_publication_status (status),
    INDEX idx_publication_type (type),
    INDEX idx_publication_category (category_id),
    INDEX idx_publication_published (published_at),
    INDEX idx_publication_scheduled (scheduled_at),
    FULLTEXT INDEX idx_publication_search (title, summary, content, search_keywords)
);

-- Tabela de relacionamento many-to-many publicações e tags
CREATE TABLE publication_tags (
    publication_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (publication_id, tag_id),
    CONSTRAINT fk_pub_tag_publication FOREIGN KEY (publication_id) REFERENCES publications(id) ON DELETE CASCADE,
    CONSTRAINT fk_pub_tag_tag FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- Tabela de arquivos das publicações
CREATE TABLE publication_files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    publication_id BIGINT NOT NULL,
    file_name VARCHAR(500) NOT NULL,
    original_name VARCHAR(500) NOT NULL,
    file_path VARCHAR(1000) NOT NULL,
    file_size BIGINT NOT NULL,
    mime_type VARCHAR(255) NOT NULL,
    file_type ENUM('PDF', 'IMAGE', 'VIDEO', 'DOCUMENT') NOT NULL,
    is_main_file BOOLEAN DEFAULT FALSE,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_file_publication FOREIGN KEY (publication_id) REFERENCES publications(id) ON DELETE CASCADE,
    INDEX idx_file_publication (publication_id),
    INDEX idx_file_type (file_type)
);

-- Tabela de versões para controle de histórico
CREATE TABLE publication_versions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    publication_id BIGINT NOT NULL,
    version_number INT NOT NULL,
    title VARCHAR(500),
    summary TEXT,
    content LONGTEXT,
    changed_by BIGINT,
    change_reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_version_publication FOREIGN KEY (publication_id) REFERENCES publications(id) ON DELETE CASCADE,
    CONSTRAINT fk_version_user FOREIGN KEY (changed_by) REFERENCES users(id),
    UNIQUE KEY unique_version (publication_id, version_number)
);

-- Inserir categorias padrão
INSERT INTO categories (name, slug, description) VALUES
('Finanças Sustentáveis', 'financas-sustentaveis', 'Artigos sobre finanças sustentáveis e ESG'),
('Pesquisas', 'pesquisas', 'Pesquisas e estudos desenvolvidos pela BRASFI'),
('Notícias', 'noticias', 'Notícias e atualizações do setor'),
('Regulamentação', 'regulamentacao', 'Informações sobre regulamentação e compliance'),
('Mercado', 'mercado', 'Análises e tendências do mercado financeiro sustentável');

-- Inserir tags padrão
INSERT INTO tags (name, slug) VALUES
('ESG', 'esg'),
('Sustentabilidade', 'sustentabilidade'),
('Finanças Verdes', 'financas-verdes'),
('Regulamentação', 'regulamentacao'),
('Investimentos', 'investimentos'),
('Biodiversidade', 'biodiversidade'),
('Mudanças Climáticas', 'mudancas-climaticas');