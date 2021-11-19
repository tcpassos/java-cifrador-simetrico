package cipher;

/**
 * Interface com metodos para a criptografia de deciframento bloco a bloco.
 */
public interface BlockCipher {
    
    public int[] encrypt(int[] block);
    public int[] decrypt(int[] block);
    
}
