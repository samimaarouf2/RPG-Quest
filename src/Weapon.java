import javax.swing.*;

public class Weapon {

    private String name;
    private int damage;
    private ImageIcon pic;

    public Weapon(String name, int damage, String imagePath) {
        this.name = name;
        this.damage = damage;
        pic = new ImageIcon(imagePath);
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public ImageIcon getPic() {
        return pic;
    }
}