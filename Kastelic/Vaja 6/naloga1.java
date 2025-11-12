/*
 * Zaženite javanski program iz področja 3segm_stevec in si oglejte njegovo
 * delovanje. Ugotovite in zapišite:
 * • S čim prožimo izjemo ?
 * • Kako je izvedeno prestrezanje izjeme ?
 * • Koliko je različnih vrst izjem in katere vrste prestrezamo?
 * o glede na dani program,
 * o v splošnem.
 */

/*
  Izjemo prožimo z:
    public void up() throws MOverFlowException {
        stanje++;
        if (stanje > 9)
            throw new MOverFlowException();
    }

    Izjemo prestrežemo z:
    try {
          nasl.up();
    } catch ( NullPointerException e){     
    } catch ( MOverFlowException e ){
            System.out.println(e);
            nasl.overflow();
    }
      
    V danem programu prestrezamo dve vrsti izjem:
    1. NullPointerException
    2. MOverFlowException

    Prestrezamo lahko vse vrste izjem, ki so podrazredi razreda
    java.lang.Throwable, kar vključuje:
    1. Checked exceptions (npr. IOException, SQLException)
    2. Unchecked exceptions (npr. NullPointerException, ArithmeticException)
    3. Errors (npr. OutOfMemoryError, StackOverflowError)
 */