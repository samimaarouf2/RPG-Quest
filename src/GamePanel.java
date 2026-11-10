import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GamePanel extends JPanel implements KeyListener, MouseListener {

    private enum GameState { SELECT, PLAYING, WIN, LOSE }

    private GameState state = GameState.SELECT;

    private ArrayList<Hero> party;
    private Hero activeHero;

    private Queue<Enemy> enemyQueue;
    private Enemy currentEnemy;

    private int enemiesDefeated = 0;
    private final int TOTAL_ENEMIES = 4;

    private Rectangle[] selectBoxes;
    private Timer timer;

    public GamePanel() {
        setPreferredSize(new Dimension(900, 600));
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        buildParty();
        buildEnemyQueue();
        buildSelectionBoxes();

        timer = new Timer(30, e -> gameTick());
        timer.start();
    }

    private void buildParty() {
        party = new ArrayList<Hero>();
        party.add(new Warrior("Sir Roland"));
        party.add(new Mage("Elowen"));
        party.add(new Archer("Kael"));
    }

    private void buildEnemyQueue() {
        enemyQueue = new LinkedList<Enemy>();

        enemyQueue.add(new Enemy("Goblin", 40, 5,
                new Color(90, 140, 60), 700, 100));

        enemyQueue.add(new Enemy("Skeleton", 55, 7,
                new Color(210, 210, 190), 700, 200));

        enemyQueue.add(new Enemy("Orc", 75, 9,
                new Color(120, 80, 40), 700, 300));

        enemyQueue.add(new Enemy("Dragon", 110, 14,
                new Color(170, 20, 20), 700, 400));
    }

    private void buildSelectionBoxes() {
        selectBoxes = new Rectangle[party.size()];

        int startX = 100;
        int y = 250;
        int w = 200;
        int h = 130;
        int gap = 50;

        for (int i = 0; i < party.size(); i++) {
            selectBoxes[i] =
                    new Rectangle(
                            startX + i * (w + gap),
                            y,
                            w,
                            h);
        }
    }

   private void gameTick() {

    if (state == GameState.PLAYING &&
            currentEnemy != null) {

        currentEnemy.moveToward(
                activeHero.getX(),
                activeHero.getY());

        if (currentEnemy.getBounds()
                .intersects(activeHero.getBounds())) {

            activeHero.takeDamage(1);

            if (!activeHero.isAlive()) {
                state = GameState.LOSE;
            }
        }
    }

    repaint();
}

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0,
                getWidth(),
                getHeight());

        switch (state) {

            case SELECT:
                drawSelectScreen(g);
                break;

            case PLAYING:
                drawGameScreen(g);
                break;

            case WIN:
                drawEndScreen(
                        g,
                        "YOU WIN!",
                        Color.GREEN);
                break;

            case LOSE:
                drawEndScreen(
                        g,
                        "YOU LOSE!",
                        Color.RED);
                break;
        }
    }
    private void drawWeaponPhoto(
        Graphics g,
        ImageIcon icon,
        int areaX,
        int areaY,
        int areaW,
        int areaH) {

    if (icon == null) return;

    int imgW = icon.getIconWidth();
    int imgH = icon.getIconHeight();

    double scale = Math.min(
            (double) areaW / imgW,
            (double) areaH / imgH);

    int drawW = (int) (imgW * scale);
    int drawH = (int) (imgH * scale);

    int drawX =
            areaX +
            (areaW - drawW) / 2;

    g.drawImage(
            icon.getImage(),
            drawX,
            areaY,
            drawW,
            drawH,
            null);
}
    private void drawSelectScreen(Graphics g) {

        g.setColor(Color.WHITE);

        g.setFont(new Font(
                "Arial",
                Font.BOLD,
                28));

        g.drawString(
                "Choose Your Hero",
                320,
                100);

        g.setFont(new Font(
                "Arial",
                Font.PLAIN,
                14));

        g.drawString(
                "Click a card to begin",
                350,
                130);

        for (int i = 0; i < party.size(); i++) {

            Hero h = party.get(i);
            Rectangle box = selectBoxes[i];

            g.setColor(h.getColor());

            g.fillRect(
                    box.x,
                    box.y,
                    box.width,
                    box.height);

            g.setColor(Color.WHITE);

            g.drawRect(
                    box.x,
                    box.y,
                    box.width,
                    box.height);

            g.setFont(new Font(
                    "Arial",
                    Font.BOLD,
                    18));

            g.drawString(
                    h.getName(),
                    box.x + 10,
                    box.y + 25);

            g.setFont(new Font(
                    "Arial",
                    Font.PLAIN,
                    13));

            g.drawString(
                    "HP: " + h.getMaxHealth(),
                    box.x + 10,
                    box.y + 50);

            g.drawString(
                    "Weapon: " +
                    h.getWeapon().getName(),
                    box.x + 10,
                    box.y + 70);

            drawWrapped(
            g,
                    h.getAbilityName(),
                    box.x + 10,
                    box.y + 95,
                    box.width - 20);
            drawWeaponPhoto(
            g,
                    h.getWeapon().getPic(),
                    box.x,
                    box.y + box.height + 15,
                    box.width,
                    180);
            drawWrapped(
            g,
                    h.getAbilityName(),
                    box.x + 10,
                    box.y + 95,
                    box.width - 20);
        }
    }

    private void drawWrapped(
            Graphics g,
            String text,
            int x,
            int y,
            int maxWidth) {

        FontMetrics fm =
                g.getFontMetrics();

        String[] words =
                text.split(" ");

        StringBuilder line =
                new StringBuilder();

        int lineY = y;

        for (String w : words) {

            String test =
                    line + w + " ";

            if (fm.stringWidth(test)
                    > maxWidth) {

                g.drawString(
                        line.toString(),
                        x,
                        lineY);

                line =
                        new StringBuilder(
                                w + " ");

                lineY +=
                        fm.getHeight();

            } else {

                line.append(w)
                        .append(" ");
            }
        }

        g.drawString(
                line.toString(),
                x,
                lineY);
    }

    private void drawGameScreen(Graphics g) {

        int hudX = 10;

        for (Hero h : party) {

            g.setColor(
                    h == activeHero
                            ? Color.YELLOW
                            : Color.DARK_GRAY);

            g.drawRect(
                    hudX,
                    10,
                    150,
                    20);

            g.setColor(
                    Color.WHITE);

            g.drawString(
                    h.getName()
                            + " ("
                            + h.getHealth()
                            + "/"
                            + h.getMaxHealth()
                            + ")",
                    hudX + 5,
                    24);

            hudX += 160;
        }

        activeHero.draw(g);

        if (currentEnemy != null) {
            currentEnemy.draw(g);
        }

        g.setColor(Color.WHITE);

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14));

        g.drawString(
                "Enemies remaining: "
                        + (TOTAL_ENEMIES - enemiesDefeated),
                720,
                24);

        g.drawString(
                "Arrow keys: move | SPACE: attack | Q: "
                        + activeHero.getAbilityName(),
                10,
                getHeight() - 15);
    }

    private void drawEndScreen(
            Graphics g,
            String msg,
            Color color) {

        g.setColor(color);

        g.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        50));

        g.drawString(
                msg,
                320,
                280);

        g.setColor(Color.WHITE);

        g.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        18));

        g.drawString(
                "Enemies defeated: "
                        + enemiesDefeated
                        + "/"
                        + TOTAL_ENEMIES,
                340,
                330);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (state == GameState.SELECT) {

            for (int i = 0;
                 i < selectBoxes.length;
                 i++) {

                if (selectBoxes[i]
                        .contains(e.getPoint())) {

                    activeHero =
                            party.get(i);

                    activeHero.setPosition(
                            100,
                            250);

                    currentEnemy =
                            enemyQueue.poll();

                    state =
                            GameState.PLAYING;

                    requestFocusInWindow();

                    return;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (state != GameState.PLAYING)
            return;

        int step = 8;

        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP:
                activeHero.move(
                        0,
                        -step,
                        getWidth(),
                        getHeight());
                break;

            case KeyEvent.VK_DOWN:
                activeHero.move(
                        0,
                        step,
                        getWidth(),
                        getHeight());
                break;

            case KeyEvent.VK_LEFT:
                activeHero.move(
                        -step,
                        0,
                        getWidth(),
                        getHeight());
                break;

            case KeyEvent.VK_RIGHT:
                activeHero.move(
                        step,
                        0,
                        getWidth(),
                        getHeight());
                break;

            case KeyEvent.VK_SPACE:
                meleeAttack();
                break;

            case KeyEvent.VK_Q:
                specialAttack();
                break;
        }
    }

    private void meleeAttack() {

        if (currentEnemy == null)
            return;

        if (activeHero.getBounds()
                .intersects(
                        currentEnemy.getBounds())) {

            activeHero.attack(
                    currentEnemy);

            checkEnemyDefeated();
        }
    }

    private void specialAttack() {

        if (currentEnemy == null)
            return;

        activeHero.useSpecialAbility(
                currentEnemy);

        checkEnemyDefeated();
    }

    private void checkEnemyDefeated() {

        if (currentEnemy != null
                && !currentEnemy.isAlive()) {

            enemiesDefeated++;

            currentEnemy =
                    enemyQueue.poll();

            if (currentEnemy == null) {
                state = GameState.WIN;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}