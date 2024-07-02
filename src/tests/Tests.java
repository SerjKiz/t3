package tests;

import inno.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Tests {
    @Test
    @DisplayName("Кэш работает")
    public void t1_1() {
        TestFraction fraction = new TestFraction(4,8);
        Fractionable fractionPtoxy = Utils.cache(fraction);
        fractionPtoxy.doubleValue();
        fractionPtoxy.doubleValue();
        Assertions.assertEquals(fraction.count,1);
    }
    @Test
    @DisplayName("Кэш для нескольких процедур работает")
    public void t1_2() {
        TestFraction fraction = new TestFraction(4,8);
        Fractionable fractionPtoxy = Utils.cache(fraction);
        fractionPtoxy.doubleValue();
        fractionPtoxy.toString();
        Assertions.assertEquals(fraction.count,2);
    }

    @Test
    @DisplayName("Возвращает правильные значения из кэша")
    public void t1_3() {
        TestFraction fraction = new TestFraction(4,8);
        Fractionable fractionPtoxy = Utils.cache(fraction);
        fractionPtoxy.doubleValue();
        double d = fractionPtoxy.doubleValue();
        Assertions.assertEquals(d,0.5);
    }

    @Test
    @DisplayName("Кэш сбрасывается")
    public void t2_1() {
        TestFraction fraction = new TestFraction(4,8);
        Fractionable fractionPtoxy = Utils.cache(fraction);
        fractionPtoxy.doubleValue();
        fractionPtoxy.toString();
        fractionPtoxy.setNum(2);
        Assertions.assertEquals(fraction.count,0);
    }

    @Test
    @DisplayName("После сброса кэша значения норм")
    public void t2_2() {
        TestFraction fraction = new TestFraction(4,8);
        Fractionable fractionPtoxy = Utils.cache(fraction);
        fractionPtoxy.doubleValue();
        fractionPtoxy.doubleValue();
        fractionPtoxy.toString();
        fractionPtoxy.setNum(8);
        double d = fractionPtoxy.doubleValue();
        Assertions.assertEquals(d,1);
    }

    @Test
    @DisplayName("Функции без кэша не влияют на кэш")
    public void t3_1() {
        TestFraction fraction = new TestFraction(4,8);
        Fractionable fractionPtoxy = Utils.cache(fraction);
        fractionPtoxy.doubleValue();
        fractionPtoxy.toString();
        fractionPtoxy.setDenum(4);
        double d = fractionPtoxy.doubleValue();
        Assertions.assertEquals(d,0.5);
    }
    @Test
    @DisplayName("Функции без кэша не сбрасывают кэш")
    public void t3_2() {
        TestFraction fraction = new TestFraction(4,8);
        Fractionable fractionPtoxy = Utils.cache(fraction);
        fractionPtoxy.doubleValue();
        fractionPtoxy.toString();
        fractionPtoxy.setDenum(4);
        Assertions.assertEquals(fraction.count,2);
    }

    @Test
    @DisplayName("Проверка очистки кэша")
    public void t4() throws InterruptedException {
        TestFraction fraction = new TestFraction(4, 8);
        Fractionable fractionPtoxy = Utils.cache(fraction);
        fractionPtoxy.doubleValue();
        fractionPtoxy.doubleValue();
        Assertions.assertEquals(fraction.count, 1); //два вызова, закэшировалось 1 раз
        Thread.sleep(100);
        fractionPtoxy.doubleValue();
        Assertions.assertEquals(fraction.count, 1);//три вызова, кэш актуален, закэшировалось 1 раз
        Thread.sleep(1500);
        fractionPtoxy.doubleValue();//четыре вызова, кэш не актуален, закэшировалось 2 раз
        Assertions.assertEquals(fraction.count, 2);

    }

}