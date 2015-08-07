package com.h6ah4i.android.example.swipesectionadvrecyclerview.swipeablewithsectionrecyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;

/**
 * Created by hasegawa on 8/7/15.
 */
public class SwipableWithSectionItemAdapter
        extends RecyclerView.Adapter<SwipableWithSectionItemAdapter.BaseItemViewHolder>
        implements SwipeableItemAdapter<SwipableWithSectionItemAdapter.BaseItemViewHolder> {

    private static final int ITEM_VIEW_TYPE_SECTION = 0;
    private static final int ITEM_VIEW_TYPE_NORMAL = 1;

    private SectionItemDataProvider mDataProvider;

    public SwipableWithSectionItemAdapter(SectionItemDataProvider dataProvider) {
        mDataProvider = dataProvider;
        setHasStableIds(true);
    }

    @Override
    public BaseItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case ITEM_VIEW_TYPE_SECTION: {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.section_item, viewGroup, false);
                return new SectionItemViewHolder(v);
            }
            case ITEM_VIEW_TYPE_NORMAL: {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.normal_item, viewGroup, false);
                return new NormalItemViewHolder(v);
            }
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void onBindViewHolder(BaseItemViewHolder baseItemViewHolder, int position) {
        SectionItemDataProvider.Item item = mDataProvider.getItem(position);

        if (item.isSection()) {
            SectionItemViewHolder vh = (SectionItemViewHolder) baseItemViewHolder;
            vh.mTextView.setText(item.getData());
        } else {
            NormalItemViewHolder vh = (NormalItemViewHolder) baseItemViewHolder;
            vh.mTextView.setText(item.getData());
        }
    }

    @Override
    public int getItemCount() {
        return mDataProvider.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataProvider.getItem(position).isSection() ? ITEM_VIEW_TYPE_SECTION : ITEM_VIEW_TYPE_NORMAL;
    }

    @Override
    public long getItemId(int position) {
        return mDataProvider.getItem(position).getId();
    }


    @Override
    public int onGetSwipeReactionType(BaseItemViewHolder holder, int position, int x, int y) {
        return mDataProvider.getItem(position).isSection()
                ? RecyclerViewSwipeManager.REACTION_CAN_NOT_SWIPE_BOTH
                : RecyclerViewSwipeManager.REACTION_CAN_SWIPE_BOTH;
    }

    @Override
    public void onSetSwipeBackground(BaseItemViewHolder holder, int position, int type) {
    }

    @Override
    public int onSwipeItem(BaseItemViewHolder holder, int position, int result) {
        if (result == RecyclerViewSwipeManager.RESULT_SWIPED_LEFT ||
                result == RecyclerViewSwipeManager.RESULT_SWIPED_RIGHT) {
            return RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_REMOVE_ITEM;
        } else {
            return RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_DEFAULT;
        }
    }

    @Override
    public void onPerformAfterSwipeReaction(BaseItemViewHolder holder, int position, int result, int reaction) {
        if (reaction == RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_REMOVE_ITEM) {
            SectionItemDataProvider.NormalItem removedItem;

            removedItem = (SectionItemDataProvider.NormalItem) mDataProvider.removeItem(position);

            SectionItemDataProvider.SectionItem sectionItem = mDataProvider.removeFromSection(removedItem);

            if (sectionItem.getItemCount() == 0) {
                // the section is empty. remove it.
                mDataProvider.removeItem(sectionItem);
            }
        }
    }

    public static abstract class BaseItemViewHolder extends AbstractSwipeableItemViewHolder {
        public BaseItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class SectionItemViewHolder extends BaseItemViewHolder {
        TextView mTextView;

        public SectionItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        public View getSwipeableContainerView() {
            return null;
        }
    }

    private static class NormalItemViewHolder extends BaseItemViewHolder {
        TextView mTextView;
        ViewGroup mContainer;

        public NormalItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
            mContainer = (ViewGroup) itemView.findViewById(R.id.container);
        }

        @Override
        public View getSwipeableContainerView() {
            return mContainer;
        }
    }
}
