package com.brasfi.model;

import com.brasfi.siteinstitucional.auth.entity.Usuario;

/**
 * Alias/Type para a classe Usuario - mantém compatibilidade com código existente
 * Não é uma entidade JPA, apenas um alias de tipo
 */
public class User {
    // Esta é apenas um alias de tipo para Usuario
    // Use Usuario diretamente no código

    public static final Class<Usuario> ENTITY_CLASS = Usuario.class;
}