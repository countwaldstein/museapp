package com.kawo.museapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kawo.museapp.dashboard.MainDashBoard;
import com.kawo.museapp.rating.PieceRatingActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class PathView extends AppCompatActivity {
    private SQLiteDatabase mDataBase;
    private DataBaseHelper mDataBaseHelper;
    FloatingActionButton button;
    FloatingActionButton deletepathbutton;

    TextView continuediscovery;
    TextView piecesratedtext;
    TextView pathtypetext;
    TextView pathsubtypetext;
    ImageView image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_view);
        Bundle bundle = getIntent().getExtras();
        final String pathtype = bundle.getString("Type");
        final String pathsubtype = bundle.getString("Subtype");
        mDataBaseHelper = new DataBaseHelper(PathView.this);
        mDataBase = mDataBaseHelper.getWritableDatabase();
        piecesratedtext = findViewById(R.id.text1);
        deletepathbutton = findViewById(R.id.deletepath);
        pathtypetext = findViewById(R.id.path);
        pathsubtypetext = findViewById(R.id.path_name);
        image1 = findViewById(R.id.image1);
        continuediscovery = findViewById(R.id.text0);

        pathsubtypetext.setText(pathsubtype);
        button = findViewById(R.id.continuepath);


        piecesratedtext.setText(getResources().getString(R.string.pieces_rated) + Integer.toString(howManyRated(pathtype, pathsubtype)));
        Log.i("how many rated", Integer.toString(howManyRated(pathtype, pathsubtype)));

        if (pathtype.equals("PIECE_TYPE")) pathsubtypetext.setText(pathsubtype.substring(1));
        if (pathtype.equals("PIECE_COMPOSER")) pathtypetext.setText(R.string.composer_path_v);
        else if (pathtype.equals("PIECE_TYPE")) pathtypetext.setText(R.string.piece_type_path_v);
        else if (pathtype.equals("PIECE_PERIOD")) pathtypetext.setText(R.string.piece_period_path_v);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("how many", Integer.toString(howManyUnratedLeft(pathtype, pathsubtype)));

                if (howManyUnratedLeft(pathtype, pathsubtype) > 0) {
                    Intent myIntent = new Intent(PathView.this, PieceRatingActivity.class);
                    myIntent.putExtra("Type", pathtype);
                    myIntent.putExtra("Subtype", pathsubtype);
                    PathView.this.startActivity(myIntent);
                    finish();
                } else {
                }
            }

        });
        if (howManyUnratedLeft(pathtype, pathsubtype) == 0) {
            continuediscovery.setText(R.string.no_pieces_to_rate);
            button.setVisibility(View.GONE);
        }
        if (getResources().getIdentifier(pathsubtype.toLowerCase(), "drawable", getPackageName()) == 0)
            image1.setVisibility(View.GONE);
        image1.setImageResource(getResources().getIdentifier(pathsubtype.toLowerCase(), "drawable", getPackageName()));

        deletepathbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(PathView.this);
                builder1.setMessage(R.string.warning_delete);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                deletePath(pathtype, pathsubtype);
                                deleteRatings(pathtype, pathsubtype);
                                Intent myIntent = new Intent(PathView.this, MainDashBoard.class);
                                if (pathtype.equals("PIECE_TYPE")) {
                                    myIntent.putExtra("DeleteM", pathsubtype.substring(1) + getResources().getString(R.string.path_deleted));
                                } else {
                                    myIntent.putExtra("DeleteM", pathsubtype + getResources().getString(R.string.path_deleted));
                                }
                                PathView.this.startActivity(myIntent);
                                finish();

                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });

    }

    public int howManyUnratedLeft(String type, String subtype) {


        int count = 0;

        String selectQuery = "SELECT   * FROM PIECES   LEFT  Join (Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID where SCORES_TYPE is null";

        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        } else return 0;


        return count;
    }

    public int howManyRated(String type, String subtype) {


        int count = 0;

        String selectQuery = "SELECT   * FROM PIECES   LEFT  Join (Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID where SCORES_TYPE is not null";

        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        } else return 0;


        return count;
    }

    public boolean deletePath(String pathtype, String pathsubtype) {
        if (mDataBase.delete("PATHS", "PATH_TYPE= '" + pathtype + "' and PATH_SUBTYPE='" + pathsubtype + "'", null) > 0) {
            return true;
        } else return false;
    }

    public boolean deleteRatings(String pathtype, String pathsubtype) {
        if (mDataBase.delete("SCORES", "SCORES_TYPE= '" + pathtype + "' and SCORES_SUBTYPE='" + pathsubtype + "'", null) > 0) {
            return true;
        } else return false;
    }
}