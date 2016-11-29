package com.example.david.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout layout1;
    Pintar pintarNegro, pintarRojo, pintarVerde, pintarAzul;
    char color = 'n';
    int grosor = 5;
    String figura = "libre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layout1 = (RelativeLayout) findViewById(R.id.content_main);
        pintarNegro = new Pintar(this);
        pintarRojo = new Pintar(this);
        pintarVerde = new Pintar(this);
        pintarAzul = new Pintar(this);
        layout1.addView(pintarRojo);
        layout1.addView(pintarVerde);
        layout1.addView(pintarAzul);
        layout1.addView(pintarNegro);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.rojo) {
            color = 'r';
            layout1.removeView(pintarRojo);
            layout1.addView(pintarRojo);
        }

        if (id == R.id.verde) {
            color = 'v';
            layout1.removeView(pintarVerde);
            layout1.addView(pintarVerde);
        }

        if (id == R.id.negro) {
            color = 'n';
            layout1.removeView(pintarNegro);
            layout1.addView(pintarNegro);
        }

        if (id == R.id.azul) {
            color = 'a';
            layout1.removeView(pintarAzul);
            layout1.addView(pintarAzul);
        }

        if (id == R.id.fino) {
            grosor = 5;
        }

        if (id == R.id.medio) {
            grosor = 20;
        }

        if (id == R.id.grueso) {
            grosor = 50;
        }

        if (id == R.id.fRojo) {
            layout1.setBackgroundColor(Color.RED);
        }

        if (id == R.id.fVerde) {
            layout1.setBackgroundColor(Color.GREEN);
        }

        if (id == R.id.fAzul) {
            layout1.setBackgroundColor(Color.BLUE);
        }

        if (id == R.id.fNegro) {
            layout1.setBackgroundColor(Color.BLACK);
        }

        if (id == R.id.fBlanco) {
            layout1.setBackgroundColor(Color.WHITE);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.restablecer) {
            layout1.removeAllViews();
            pintarNegro = new Pintar(this);
            pintarRojo = new Pintar(this);
            pintarVerde = new Pintar(this);
            pintarAzul = new Pintar(this);
            layout1.addView(pintarRojo);
            layout1.addView(pintarVerde);
            layout1.addView(pintarAzul);
            layout1.addView(pintarNegro);
        } else if (id == R.id.salir) {
            System.exit(0);
        } else if (id == R.id.circulo) {
            figura = "circulo";
        } else if (id == R.id.cuadrado) {
            figura = "cuadrado";
        } else if (id == R.id.libre) {
            figura = "libre";
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Pintar extends View {
        float x = 50;
        float y = 50;
        String accion = "accion";
        Path path = new Path();

        public Pintar(Context context) {
            super(context);
        }

        public void onDraw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(grosor);
            paint.setColor(color());
            canvas.drawPath(path, paint);

            if(figura.equals("libre")){
                canvas.drawPath(path, paint);

                if (accion == "down") {
                    path.moveTo(x, y);
                }
                if (accion == "move") {
                    path.lineTo(x, y);
                }
            }

            if(figura.equals("cuadrado")) {
                canvas.drawRect(x, y, x + 250, y + 150, paint);
            }

            if(figura.equals("circulo")) {
                canvas.drawCircle(x, y, 100, paint);
            }
        }

        public int color(){
            switch (color){
                case 'a': return Color.BLUE;
                case 'r': return Color.RED;
                case 'v': return Color.GREEN;
                case 'n': return Color.BLACK;
            }
            return Color.BLACK;
        }

        public boolean onTouchEvent(MotionEvent e) {
            x = e.getX();
            y = e.getY();

            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                accion = "down";
            }
            if (e.getAction() == MotionEvent.ACTION_MOVE) {
                accion = "move";
            }

            invalidate();
            return true;
        }
    }
}
