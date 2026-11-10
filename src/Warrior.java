import java.awt.*;

public class Warrior extends Hero {

    public Warrior(String name) {
        super(
                name,
                120,
                15,
                new Color(190, 40, 40),
                new Weapon(
                        "Sword",
                        10,
                        "images/sword.jpg"));
    }

    @Override
    public void useSpecialAbility(Enemy target) {

        int damage =
                (attackPower + weapon.getDamage()) * 2;

        target.takeDamage(damage);

        System.out.println(
                name +
                " used Power Strike for "
                + damage +
                " damage");
    }

    @Override
    public String getAbilityName() {
        return "Power Strike";
    }
}