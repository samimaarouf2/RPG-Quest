import java.awt.*;

public class Mage extends Hero {

    public Mage(String name) {
        super(
                name,
                80,
                8,
                new Color(50, 90, 200),
                new Weapon(
                        "Staff",
                        6,
                        "images/staff.jpg"));
    }

    @Override
    public void useSpecialAbility(Enemy target) {

        int damage =
                attackPower +
                weapon.getDamage() +
                20;

        target.takeDamage(damage);

        System.out.println(
                name +
                " used Fireball for "
                + damage +
                " damage");
    }

    @Override
    public String getAbilityName() {
        return "Fireball";
    }
}