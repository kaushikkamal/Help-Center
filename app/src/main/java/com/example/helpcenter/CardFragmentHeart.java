package com.example.helpcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CardFragmentHeart extends Fragment {

    private CardView cardView;
    String name;

    public static Fragment getInstance(int position) {
        CardFragmentHeart f = new CardFragmentHeart();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager, container, false);

        cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        ImageView docPic = (ImageView) view.findViewById(R.id.docPic);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView docDescription = (TextView) view.findViewById(R.id.docDescription);
        TextView docExp = (TextView) view.findViewById(R.id.docExp);
        TextView docFees = (TextView) view.findViewById(R.id.docFees);

        Button button = (Button)view.findViewById(R.id.button);

        int pageNumber = getArguments().getInt("position");

        if (pageNumber == 0){
            docPic.setImageResource(R.drawable.female);
            docDescription.setText("A well certified doctor best known for her behaviour");
            docExp.setText("Experience : 7 years");
            docFees.setText("Fees : ₹450");
            title.setText("Dr. S.Sharma");
        }
        else if (pageNumber == 1){
            docPic.setImageResource(R.drawable.male);
            docDescription.setText("A well certified doctor best known for his behaviour");
            docExp.setText("Experience : 5 years");
            docFees.setText("Fees : ₹400");
            title.setText("Dr. K.Bordoloi");

        }
        else if (pageNumber == 2){
            docPic.setImageResource(R.drawable.female);
            docDescription.setText("A well certified doctor best known for her behaviour");
            docExp.setText("Experience : 4 years");
            docFees.setText("Fees : ₹350");
            title.setText("Dr. P.S.Hazarika");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),Form.class);
                int pos = getArguments().getInt("position");
                if (pos == 0){
                    name = "Dr S Sharma";
                }
                else if (pos == 1){
                    name = "Dr K Bordoloi";
                }
                else if (pos == 2){
                    name = "Dr PS Hazarika";
                }
                i.putExtra("docName",name);
                i.putExtra("deptN","Heart");
                startActivity(i);
            }
        });
        return view;
    }
    public CardView getCardView() {
        return cardView;
    }
}

