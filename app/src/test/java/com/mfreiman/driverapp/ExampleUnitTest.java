package com.mfreiman.driverapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mfreiman.driverapp.model.backend.FactoryMethed;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);


    }

    static public ArrayList<String> Rides;
    static boolean f=true;

        @Test
        public void MakingListIsCorrect()
        {
            try {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Travels");
                System.out.println(rootRef.getKey().toString());
                /*
                rootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (f == true) {
                            for (DataSnapshot key : dataSnapshot.getChildren()
                                    ) {

                                Rides.add((String) key.getValue().toString());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                System.out.println(FactoryMethed.getManager().getRidesList().get(0));
                */
            }
            catch (Exception ex)
            {
             ex.printStackTrace();

            }
    }
    }
