/**
 * Converte uma string em um formato de slug amigável para URLs.
 * Ex: "Olá Mundo!" se torna "ola-mundo"
 * @param text O texto a ser convertido.
 * @returns O slug gerado.
 */
export const generateSlug = (text: string): string => {
    return text
        .toString()
        .toLowerCase()
        .trim()
        .replace(/\s+/g, '-')           // Substitui espaços por -
        .replace(/[^\w\-]+/g, '')       // Remove caracteres inválidos
        .replace(/\-\-+/g, '-')         // Substitui múltiplos - por um único -
        .replace(/^-+/, '')             // Remove - do início
        .replace(/-+$/, '');            // Remove - do final
};