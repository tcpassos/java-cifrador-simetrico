# Java Cifrador Simétrico
Simples algoritmo para a criptografia e deciframento de arquivos em blocos de 48 bits, usando uma chave de 32 bits.
![Janela principal do programa](https://i.imgur.com/7JgsmNi.png)
 <br>
 A criptografia do arquivo conta com 3 etapas:<br>
  - Key Scheduling:
  - Criptografia em blocos
  - Aplicação do modo de operação CBC

## Key Scheduling
A classe `PassonKey` é responsável pela geração das sub-chaves usadas no algoritmo de criptografia.
![Key scheduling](https://i.imgur.com/tSLr2Yh.png)
O processo de key scheduling recebe uma chave de 4 bytes do usuário e tem início submetendo essa chave a um processo de permutação de 32 bits para 32 bits, seguido da aplicação da S-Box nos bytes resultantes.<br>
Em seguida são geradas 5 sub-chaves de 6 bytes ao executar 5 rounds do seguinte processamento:<br>
Os 4 bytes da chave (A, B, C e D) são dispostos em uma matriz 2x2 e sofrem uma expansão para uma matriz 3x3, gerando os bytes (E, F, G, H e I). A geração de cada byte da periferia da matriz 3x3 é dado a partir de uma operação XOR dos 2 bytes que estiverem na mesma linha (horizontal) ou na mesma coluna (vertical) dessa matriz, mas antes esses 2 bytes são deslocados de acordo com sua posição, que pode resultar em um deslocamento de 1 ou 2 bits para esquerda.<br>
Após a expansão para 9 bytes são geradas 2 partes da sub-chave e, em seguida, as partes são unidas em uma única chave. A primeira parte com 3 bytes é resultante da operação XOR entre a sequência de bytes [A, B, E] com os bytes [H, I, G]. Já a segunda parte é resultante de uma operação XOR entre [C, D, F] e [I, G, H].<br>
Após a geração da sub-chave K(n) os bytes [E, F, G, H] da matriz 3x3 assumem a posição de [A, B, C, D] em uma nova matriz 2x2 e iniciam o próximo round a partir da expansão da matriz.<br>
Neste algoritmo de key-scheduling buscamos utilizar o espaço dos bits que constituem a chave de forma igualmente distribuída e equilibrada. Além da permutação e substituição de bits inicial que aumentaram os aspectos de difusão e confusão do processo, procuramos maximizar essas propriedades ao deslocar os bits dos sub-blocos da matriz antes de realizar a operação XOR entre eles.

## Implementando um cifrador de bloco
A interface `BlockCipher` possui contratos para a implementação de um algoritmo para criptografar um bloco.
```java
public interface BlockCipher {
    
    public int[] encrypt(int[] block);
    public int[] decrypt(int[] block);
    public int getBlockSize();
    
}
```
## PassonBlockCipher
A classe `PassonBlockCipher` contém a lógica da criptografia. Ela implementa os métodos da interface `BlockCipher` e recebe a chave do usuário em seu construtor.
```java
// Fluxo de criptografia de um bloco
public int[] encrypt(int[] block) {
    BigInteger encryptedBlock = JoinOperations.joinBytes(block);
    for(long key: keys) {
        // P-box 48 bits -> 48 bits
        encryptedBlock = Permutations.permute48(encryptedBlock);
        // S-box
        encryptedBlock = SBox.transform(encryptedBlock, BLOCK_SIZE);
        // bloco XOR K(n)
        encryptedBlock = encryptedBlock.xor(BigInteger.valueOf(key));
        // bloco << K(n)
        int shift = (int) (key % (Byte.SIZE * BLOCK_SIZE/2));
        long shiftedBlock = ShiftOperations.circularShiftLeft(encryptedBlock.longValue(), BLOCK_SIZE * Byte.SIZE, shift);
        encryptedBlock = BigInteger.valueOf(shiftedBlock);
    }
    return SplitOperations.getByteSegments(encryptedBlock, block.length);
}
```
O processo de criptografia consiste em executar 5 rounds com as seguintes etapas:

 - Permutação
 - S-box
 - Operação XOR entre o bloco e a sub-chave
 - Deslocamento circular dos bits do bloco de acordo com o valor da sub-chave

Pode ser levado em consideração que o deslocamento final dificilmente será o mesmo já que ele é dependente do valor da sub-chave.

![Key scheduling](https://i.imgur.com/XaVhBLd.png)

## Modo de operação CBC
A classe `CbcBlockCipher` é responsável pela implementação do modo de operação CBC. Ela recebe o cifrador que implementa `BlockCipher` em seu construtor e se adapta para aplicar o CBC de acordo com o comprimento dos blocos do cifrador interno.
```java
// Construtor
public CbcBlockCipher(BlockCipher encryptor) {
    this.encryptor = encryptor;
    // Valor utilizado na primeira operação do CBC
    this.salt = Generators.decimalsOfPi(encryptor.getBlockSize());
}
```
Uma vez que a operação XOR do modo de operação tem precisa de um valor inicial até que o segundo round assuma o valor do bloco anterior, optamos por utilizar os decimais de PI para essa operação.

## Leitura em blocos
Para facilitar a leitura dos blocos do arquivo, foi criada a classe `InputBlockStream`, que recebe o tamanho do bloco em seu construtor e disponibiliza os métodos `nextBlock()` e `hasNext()`.
```java
InputBlockStream bstream = new InputBlockStream(reader, blockCipher.getBlockSize());
_writeHeader(writer);
// Le o arquivo enquanto existirem bytes disponiveis
while(bstream.hasNext()) {
    int[] block = bstream.nextBlock();
    _writeBytes(writer, blockCipher.encrypt(block));
}
```

## Tratamento de padding
A classe `FileEncriptor` é responsável por criptografar e descriptografar um arquivo de acordo com o `BlockCipher` recebido em seu construtor. Essa classe conta com um tratamento de padding, evitando que bytes adicionais sejam escritos no arquivo após decodificado.<br>
Para isso é escrito no primeiro byte do arquivo codificado o padding que deverá ser desconsiderado durante a decodificação:
```java
private void _writeHeader(OutputStream writer) throws IOException {
    int blockSize = blockCipher.getBlockSize();
    int remainder = (int) (file.length() % blockSize);
    int padding = remainder == 0 ? 0 : blockSize - remainder;
    writer.write(padding);
}
```
