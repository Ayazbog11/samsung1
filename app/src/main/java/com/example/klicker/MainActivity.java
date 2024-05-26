package com.example.klicker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Game game;
    private Upgrade upgrade1;
    private Upgrade upgrade2;
    private Upgrade upgrade3;

    private TextView tvClicks, tvLevel, tvUpgrade1Cost, tvUpgrade2Cost, tvUpgrade3Cost, tvSuperStatus;
    private Button btnClick, btnUpgrade1, btnUpgrade2, btnUpgrade3, btnSuper, btnUpgradeSuperDuration, btnUpgradeSuperCooldown;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        upgrade1 = new Upgrade(50, 25, 1); // Начальная стоимость, увеличение стоимости, добавление кликов
        upgrade2 = new Upgrade(100, 50, 2);
        upgrade3 = new Upgrade(150, 75, 3);

        tvClicks = findViewById(R.id.tvClicks);
        tvLevel = findViewById(R.id.tvLevel);
        tvUpgrade1Cost = findViewById(R.id.tvUpgrade1Cost);
        tvUpgrade2Cost = findViewById(R.id.tvUpgrade2Cost);
        tvUpgrade3Cost = findViewById(R.id.tvUpgrade3Cost);
        tvSuperStatus = findViewById(R.id.tvSuperStatus);

        btnClick = findViewById(R.id.btnClick);
        btnUpgrade1 = findViewById(R.id.btnUpgrade1);
        btnUpgrade2 = findViewById(R.id.btnUpgrade2);
        btnUpgrade3 = findViewById(R.id.btnUpgrade3);
        btnSuper = findViewById(R.id.btnSuper);
        btnUpgradeSuperDuration = findViewById(R.id.btnUpgradeSuperDuration);
        btnUpgradeSuperCooldown = findViewById(R.id.btnUpgradeSuperCooldown);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.click();
                updateUI();
            }
        });

        btnUpgrade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upgrade1.applyUpgrade(game)) {
                    updateUI();
                }
            }
        });

        btnUpgrade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upgrade2.applyUpgrade(game)) {
                    updateUI();
                }
            }
        });

        btnUpgrade3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upgrade3.applyUpgrade(game)) {
                    updateUI();
                }
            }
        });

        btnSuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.activateSuper()) {
                    updateUI();
                    startSuperTimer();
                }
            }
        });

        btnUpgradeSuperDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cost = 200; // Стоимость апгрейда продолжительности супер режима
                if (game.getClicks() >= cost) {
                    game.spendClicks(cost);
                    game.upgradeSuperDuration(5000); // Увеличение продолжительности на 5 секунд
                    updateUI();
                }
            }
        });

        btnUpgradeSuperCooldown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cost = 200; // Стоимость апгрейда кулдауна супер режима
                if (game.getClicks() >= cost) {
                    game.spendClicks(cost);
                    game.upgradeSuperCooldown(60000); // Уменьшение кулдауна на 1 минуту
                    updateUI();
                }
            }
        });

        startSuperTimer();
        updateUI();
    }

    private void startSuperTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                game.updateSuper();
                updateUI();
                if (game.isSuperActive() || System.currentTimeMillis() < game.getSuperEndTime()) {
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private void updateUI() {
        tvClicks.setText("Clicks: " + game.getClicks());
        tvLevel.setText("Level: " + game.getLevel());
        tvUpgrade1Cost.setText("Cost: " + upgrade1.getCost());
        tvUpgrade2Cost.setText("Cost: " + upgrade2.getCost());
        tvUpgrade3Cost.setText("Cost: " + upgrade3.getCost());

        if (game.isSuperActive()) {
            tvSuperStatus.setText("Super Active! Ends in: " + (game.getSuperEndTime() - System.currentTimeMillis()) / 1000 + "s");
        } else {
            long cooldown = game.getSuperEndTime() - System.currentTimeMillis();
            if (cooldown > 0) {
                tvSuperStatus.setText("Super on Cooldown: " + cooldown / 1000 + "s");
            } else {
                tvSuperStatus.setText("Super Ready!");
            }
        }
    }
}
