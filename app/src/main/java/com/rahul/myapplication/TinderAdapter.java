package com.rahul.myapplication;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahul.myapplication.Model.UserData;
import com.rahul.myapplication.Utils.CircularImageView;
import com.rahul.myapplication.Utils.GeneralUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TinderAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Click click;
    private List<UserData> data;
    private Context context;

    public TinderAdapter(List<UserData> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        if (rowView == null) {

            rowView = inflater.inflate(R.layout.card_item, parent, false);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.cardImage = (CircularImageView) rowView.findViewById(R.id.profileImageView);
            viewHolder.txtName = (TextView) rowView.findViewById(R.id.nameTextView);
            viewHolder.txtDetails = (TextView) rowView.findViewById(R.id.addressTextView);
            viewHolder.imgName = (ImageView) rowView.findViewById(R.id.imageView7);
            viewHolder.imgDob = (ImageView) rowView.findViewById(R.id.imageView6);
            viewHolder.imgLocation = (ImageView) rowView.findViewById(R.id.imageView8);
            viewHolder.imgNumber = (ImageView) rowView.findViewById(R.id.imageView9);
            viewHolder.imgLoc = (ImageView) rowView.findViewById(R.id.imageView10);
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        click = new Click(viewHolder, position);
        viewHolder.imgName.setOnClickListener(click);
        viewHolder.imgDob.setOnClickListener(click);
        viewHolder.imgLocation.setOnClickListener(click);
        viewHolder.imgNumber.setOnClickListener(click);
        viewHolder.imgLoc.setOnClickListener(click);

        // Default Name is selected
        viewHolder.txtName.setText("Name");
        viewHolder.txtDetails.setText(GeneralUtils.getCapitalized(data.get(position).getUser().getName().getTitle()) + " " +
                GeneralUtils.getCapitalized(data.get(position).getUser().getName().getFirst()) + " " +
                GeneralUtils.getCapitalized(data.get(position).getUser().getName().getLast()));
        setImageResource(R.id.imageView7, viewHolder);

        Picasso.get().load(data.get(position).getUser().getPicture()).into(viewHolder.cardImage, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("Image ", "Success");
            }

            @Override
            public void onError(Exception e) {
                Log.e("Image ", "Failure " + e);
            }
        });

        return rowView;
    }

    // Note
    // I can use radio button to the below function instead of ImageView
    public void setImageResource(int id, ViewHolder viewHolder) {
        reset(viewHolder);
        switch (id) {
            case R.id.imageView6:
                viewHolder.imgDob.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_perm_contact_calendar_green_24dp));
                break;
            case R.id.imageView7:
                viewHolder.imgName.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_face_green_24dp));
                break;
            case R.id.imageView8:
                viewHolder.imgLocation.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_map_green_24dp));
                break;
            case R.id.imageView9:
                viewHolder.imgNumber.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_call_green_24dp));
                break;
            case R.id.imageView10:
                viewHolder.imgLocation.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_lock_outline_green_24dp));
                break;

        }
    }

    public void reset(ViewHolder viewHolder) {
        viewHolder.imgName.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_face_black_24dp));
        viewHolder.imgDob.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_perm_contact_calendar_black_24dp));
        viewHolder.imgLoc.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_lock_outline_black_24dp));
        viewHolder.imgNumber.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_call_gray_24dp));
        viewHolder.imgLocation.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_map_gray_24dp));
    }

    public static class ViewHolder {
        public static TextView txtName, txtDetails;
        public static CircularImageView cardImage;
        public static ImageView imgName, imgDob, img, imgLocation, imgNumber, imgLoc;
    }

    public class Click implements View.OnClickListener {
        ViewHolder viewHolder;
        int position;

        public Click(ViewHolder viewHolder, int position) {
            this.position = position;
            this.viewHolder = viewHolder;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageView6:
                    viewHolder.txtName.setText("Date of Birth");
                    viewHolder.txtDetails.setText(GeneralUtils.epochTodte(data.get(position).getUser().getDob()));
                    setImageResource(R.id.imageView6, viewHolder);
                    break;
                case R.id.imageView7:
                    viewHolder.txtName.setText("Name");
                    viewHolder.txtDetails.setText(GeneralUtils.getCapitalized(data.get(position).getUser().getName().getTitle()) + " " +
                            GeneralUtils.getCapitalized(data.get(position).getUser().getName().getFirst()) + " " +
                            GeneralUtils.getCapitalized(data.get(position).getUser().getName().getLast()));
                    setImageResource(R.id.imageView7, viewHolder);
                    break;
                case R.id.imageView8:
                    viewHolder.txtName.setText("Location");
                    viewHolder.txtDetails.setText(data.get(position).getUser().getLocation().getStreet() + ", " +
                            data.get(position).getUser().getLocation().getCity() + ", " +
                            data.get(position).getUser().getLocation().getState() + ", " +
                            data.get(position).getUser().getLocation().getZip());
                    setImageResource(R.id.imageView8, viewHolder);
                    break;
                case R.id.imageView9:
                    viewHolder.txtName.setText("Contact");
                    viewHolder.txtDetails.setText(data.get(position).getUser().getCell());
                    setImageResource(R.id.imageView9, viewHolder);
                    break;
                case R.id.imageView10:
                    // Don't understood what to show that's why kept it blank
                    break;
            }
        }
    }
}
