/* Definirajte razred Smer, ki bo predstavljal smer gibanja. Gibanje naj bo zgolj 90-stopinjsko (vzporedno z eno od osi koordinatnega sistema). 
Začetna smer naj bo 'desno', to je +1 po koordinati X in 0 po koordinati Y. Definicija naj omogoča zasuk oz. spremembo smeri levo (npr. v 0,1) ali desno, npr. v (0,-1). 
Predpis je podan z razredom:

abstract class SmerAbstract {
    private int x,x; // smer

    public abstract void levo();
    public abstract void desno();
}
*/

abstract class SmerAbstract {
    protected int x; // smer po x osi
    protected int y; // smer po y osi

    public abstract void levo();
    public abstract void desno();
}

class Smer extends SmerAbstract {
    public Smer() {
        this.x = 1; // začetna smer desno
        this.y = 0;
    }

    @Override
    public void levo() {
        int temp = x;
        x = -y;
        y = temp;
    }

    @Override
    public void desno() {
        int temp = x;
        x = y;
        y = -temp;
    }

    @Override
    public String toString() {
        return "Smer: (" + x + ", " + y + ")";
    }
}