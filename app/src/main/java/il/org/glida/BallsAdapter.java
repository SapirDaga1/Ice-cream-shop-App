package il.org.glida;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BallsAdapter extends ArrayAdapter {

    public BallsAdapter(@NonNull Context context, ArrayList<BallItem> ballItems) {
        super(context, 0,ballItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,parent,false);
        }
        ImageView ballImage = convertView.findViewById(R.id.ball_image);
        TextView ballName = convertView.findViewById(R.id.ball_flavour);

        BallItem currentBall = (BallItem) getItem(position);
        if(currentBall != null){
            ballImage.setImageResource(currentBall.getBallImage());
            ballName.setText(currentBall.getBallName());
        }
        return convertView;
    }
}
