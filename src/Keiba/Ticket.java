package Keiba;
import java.util.ArrayList;
import java.util.List;

class Ticket {
    private final List<Horse> pick = new ArrayList<>();
    Ticket(Horse first) { this.pick.add(first); }
    Ticket(Horse first, Horse second, Horse third) {
        this.pick.add(first);
        this.pick.add(second);
        this.pick.add(third);
    }
    public double win() {
        if (pick.get(0).getPlacement() == 1) {
            return pick.get(0).getOdds();
        }
        return 0.0;
    }
    public double place() {
        if (pick.get(0).getPlacement() < 4) {
            return pick.get(0).getOdds() / 3.0;
        }
        return 0.0;
    }
    public double trifecta() {
        if (pick.get(0).getPlacement() == 1 &&
                pick.get(1).getPlacement() == 2 &&
                pick.get(2).getPlacement() == 3) {
            return pick.get(0).getOdds() * pick.get(1).getOdds() * pick.get(2).getOdds();
        }
        return 0.0;
    }
    public double trio() {
        if (pick.get(0).getPlacement() < 4 &&
                pick.get(1).getPlacement() < 4 &&
                pick.get(2).getPlacement() < 4) {
            return pick.get(0).getOdds() * pick.get(1).getOdds() * pick.get(2).getOdds() / 6.0;
        }
        return 0.0;
    }
}
