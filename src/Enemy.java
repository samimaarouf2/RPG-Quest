import java.awt.*;

public class Enemy {

    private String name;
    private int health;
    private int maxHealth;
    private int attackPower;
    private int x;
    private int y;

    private int width = 50;
    private int height = 50;

    private Color color;

    private int damageShown;
    private long damageTime;

    public Enemy(String name, int health, int attackPower,
                 Color color, int x, int y) {

        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void moveToward(int targetX, int targetY) {

        if (x < targetX) {
            x += 2;
        } else if (x > targetX) {
            x -= 2;
        }

        if (y < targetY) {
            y += 2;
        } else if (y > targetY) {
            y -= 2;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void takeDamage(int damage) {

        damageShown = damage;
        damageTime = System.currentTimeMillis();

        health -= damage;

        if (health < 0) {
            health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void draw(Graphics g) {

        g.setColor(color);
        g.fillRect(x, y, width, height);

        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);

        g.drawString(name, x, y - 18);

        g.setColor(Color.RED);
        g.fillRect(x, y - 12, width, 6);

        g.setColor(Color.GREEN);

        int bar =
                (int)((double) health /
                        maxHealth * width);

        g.fillRect(x, y - 12, bar, 6);

        if (System.currentTimeMillis() - damageTime < 1000) {

            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 18));

            g.drawString(
                    "-" + damageShown,
                    x + 10,
                    y - 25);
        }
    }

    public String getName() {
        return name;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}