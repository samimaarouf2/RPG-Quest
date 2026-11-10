import java.awt.*;

public abstract class Hero {

    protected String name;
    protected int health;
    protected int maxHealth;
    protected int attackPower;

    protected int x;
    protected int y;

    protected int w = 50;
    protected int h = 50;

    protected Color color;
    protected Weapon weapon;

    public Hero(String name,
                int health,
                int attackPower,
                Color color,
                Weapon weapon) {

        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
        this.color = color;
        this.weapon = weapon;
    }

    public abstract void useSpecialAbility(
            Enemy target);

    public abstract String getAbilityName();

    public void move(int dx,
                     int dy,
                     int screenW,
                     int screenH) {

        x = Math.max(
                0,
                Math.min(
                        screenW - w,
                        x + dx));

        y = Math.max(
                0,
                Math.min(
                        screenH - h,
                        y + dy));
    }

    public void attack(Enemy target) {

        int damage =
                attackPower +
                weapon.getDamage();

        target.takeDamage(damage);
    }

    public void takeDamage(int dmg) {

        health -= dmg;

        if (health < 0) {
            health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public void draw(Graphics g) {

        g.setColor(color);
        g.fillRect(x, y, w, h);

        g.setColor(Color.WHITE);
        g.drawRect(x, y, w, h);

        g.drawString(name, x, y - 18);

        g.setColor(Color.RED);
        g.fillRect(x, y - 12, w, 6);

        g.setColor(Color.GREEN);

        int bar =
                (int)((double)health /
                maxHealth * w);

        g.fillRect(x, y - 12, bar, 6);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Color getColor() {
        return color;
    }
}