import java.awt.*;

public class Archer extends Hero {

    public Archer(String name) {
        super(
                name,
                95,
                10,
                new Color(60, 150, 70),
                new Weapon(
                        "Bow",
                        8,
                        "images/bow.jpg"));
    }

    @Override
    public void useSpecialAbility(Enemy target) {

        int damage =
                attackPower +
                weapon.getDamage() +
                12;

        target.takeDamage(damage);

        System.out.println(
                name +
                " used Multi Shot for "
                + damage +
                " damage");
    }

    @Override
    public String getAbilityName() {
        return "Multi Shot";
    }
}