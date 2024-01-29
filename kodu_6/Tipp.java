public class Tipp {
    String info;
    Tipp v;
    Tipp p;
    int x; // abiväli

    Tipp(String info, Tipp v, Tipp p) {
        this.info = info;
        this.v = v;
        this.p = p;
    }

    Tipp(String info) {
        this.info = info;
    }
}