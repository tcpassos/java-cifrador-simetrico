package core.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 */
public class Generators {

    /**
     * Retorna o valor dos decimais de PI com o comprimento informado
     *
     * @param length Comprimento dos decimais
     * @return {@code BigInteger}
     */
    public static BigInteger decimalsOfPi(int length) {
        BigDecimal pi = pi(length);
        BigDecimal fraction = pi.remainder(BigDecimal.ONE);
        return fraction.movePointRight(pi.scale()).toBigInteger();
    }

    /**
     * Calcula PI usando a formula de Bellard
     *
     * @param digits Quantidade de digitos apos a virgula
     * @return {@code BigDecimal}
     */
    public static BigDecimal pi(int digits) {
        BigDecimal num2power6 = new BigDecimal(64);
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < 10; i++) {
            BigDecimal tmp;
            BigDecimal term;
            BigDecimal divisor;
            term = new BigDecimal(-32);
            divisor = new BigDecimal(4 * i + 1);
            tmp = term.divide(divisor, digits, BigDecimal.ROUND_FLOOR);
            term = new BigDecimal(-1);
            divisor = new BigDecimal(4 * i + 3);
            tmp = tmp.add(term.divide(divisor, digits, BigDecimal.ROUND_FLOOR));
            term = new BigDecimal(256);
            divisor = new BigDecimal(10 * i + 1);
            tmp = tmp.add(term.divide(divisor, digits, BigDecimal.ROUND_FLOOR));
            term = new BigDecimal(-64);
            divisor = new BigDecimal(10 * i + 3);
            tmp = tmp.add(term.divide(divisor, digits, BigDecimal.ROUND_FLOOR));
            term = new BigDecimal(-4);
            divisor = new BigDecimal(10 * i + 5);
            tmp = tmp.add(term.divide(divisor, digits, BigDecimal.ROUND_FLOOR));
            term = new BigDecimal(-4);
            divisor = new BigDecimal(10 * i + 7);
            tmp = tmp.add(term.divide(divisor, digits, BigDecimal.ROUND_FLOOR));
            term = new BigDecimal(1);
            divisor = new BigDecimal(10 * i + 9);
            tmp = tmp.add(term.divide(divisor, digits, BigDecimal.ROUND_FLOOR));
            int s = ((1 - ((i & 1) << 1)));
            divisor = new BigDecimal(2);
            divisor = divisor.pow(10 * i).multiply(new BigDecimal(s));
            sum = sum.add(tmp.divide(divisor, digits, BigDecimal.ROUND_FLOOR));
        }
        sum = sum.divide(num2power6, digits, BigDecimal.ROUND_FLOOR);
        return sum;
    }

}
