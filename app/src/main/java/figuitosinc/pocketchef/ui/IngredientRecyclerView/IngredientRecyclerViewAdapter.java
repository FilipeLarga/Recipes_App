package figuitosinc.pocketchef.ui.IngredientRecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import figuitosinc.pocketchef.R;
import figuitosinc.pocketchef.database.IngredientEntity;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.IngredientViewHolder> {

    private List<IngredientEntity> data;
    private Context context;
    private IngredientRecyclerViewOnClickListener ingredientRecyclerviewOnClickListener;

    public IngredientRecyclerViewAdapter(Context context, IngredientRecyclerViewOnClickListener ingredientRecyclerviewOnClickListener) {
        data = new ArrayList<>();
        this.context = context;
        this.ingredientRecyclerviewOnClickListener = ingredientRecyclerviewOnClickListener;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ingredient_filter_ingredients_list_row, viewGroup, false);
        final IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientRecyclerviewOnClickListener.onItemClick(ingredientViewHolder.ingredientName.getText().toString(), ingredientViewHolder.getAdapterPosition());
            }
        });
        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientViewHolder ingredientViewHolder, final int i) {
        ingredientViewHolder.ingredientName.setText(data.get(i).IngredientName);
    }

    public IngredientEntity getIngredient(int i) {
        return data.get(i);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setData(List<IngredientEntity> newList) {
        IngredientDiffCallback ingredientDiffCallback = new IngredientDiffCallback(data, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(ingredientDiffCallback, true);
        data.clear();
        data.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientName;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_filter_ingredients_list_row_textview);
        }
    }

    public class IngredientDiffCallback extends DiffUtil.Callback {

        private final List<IngredientEntity> oldList;
        private final List<IngredientEntity> newList;

        public IngredientDiffCallback(List<IngredientEntity> oldList, List<IngredientEntity> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int i, int i1) {
            return oldList.get(i).IngredientName.equals(newList.get(i1).IngredientName);
        }

        @Override
        public boolean areContentsTheSame(int i, int i1) {
            return oldList.get(i).IngredientName.equals(newList.get(i1).IngredientName);
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }

}
