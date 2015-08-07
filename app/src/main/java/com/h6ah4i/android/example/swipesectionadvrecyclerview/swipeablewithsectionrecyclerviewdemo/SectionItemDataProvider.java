package com.h6ah4i.android.example.swipesectionadvrecyclerview.swipeablewithsectionrecyclerviewdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasegawa on 8/7/15.
 */
public class SectionItemDataProvider {
    public static abstract class Item {
        public abstract boolean isSection();

        public abstract long getId();

        public abstract String getData();
    }

    public static class SectionItem extends Item {
        private final long mId;
        private List<NormalItem> mItems;

        public SectionItem(long id) {
            mId = id;
            mItems = new ArrayList<>();
        }

        @Override
        public boolean isSection() {
            return true;
        }

        @Override
        public long getId() {
            return mId;
        }

        @Override
        public String getData() {
            return "Section (ID: " + mId + ")";
        }

        public void addNormalItem(NormalItem item) {
            mItems.add(item);
        }

        public void removeNormalItem(Item removedItem) {
            mItems.remove(removedItem);
        }

        public int getItemCount() {
            return mItems.size();
        }
    }

    public static class NormalItem extends Item {
        private final SectionItem mSectionItem;
        private final long mId;

        public NormalItem(SectionItem sectionItem, long id) {
            mSectionItem = sectionItem;
            mId = id;
        }

        @Override
        public boolean isSection() {
            return false;
        }

        @Override
        public long getId() {
            return mId;
        }

        @Override
        public String getData() {
            return "Item (ID: " + mId + ")";
        }

        public SectionItem getSectionItem() {
            return mSectionItem;
        }
    }

    private List<Item> mItems;


    public SectionItemDataProvider() {
        mItems = new ArrayList<>();

        // generate example data
        long itemId = 0;

        {
            SectionItem section = new SectionItem(itemId++);
            mItems.add(section);

            addNormalItem(mItems, itemId++, section);
            addNormalItem(mItems, itemId++, section);
            addNormalItem(mItems, itemId++, section);
        }

        {
            SectionItem section = new SectionItem(itemId++);
            mItems.add(section);

            addNormalItem(mItems, itemId++, section);
            addNormalItem(mItems, itemId++, section);
            addNormalItem(mItems, itemId++, section);
        }

        {
            SectionItem section = new SectionItem(itemId++);
            mItems.add(section);

            addNormalItem(mItems, itemId++, section);
            addNormalItem(mItems, itemId++, section);
            addNormalItem(mItems, itemId++, section);
        }
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }

    public Item removeItem(int position) {
        return mItems.remove(position);
    }

    public void removeItem(Item item) {
        mItems.remove(item);
    }

    public SectionItem removeFromSection(NormalItem removedItem) {
        SectionItem sectionItem = removedItem.getSectionItem();
        sectionItem.removeNormalItem(removedItem);
        return sectionItem;
    }

    public int getItemCount() {
        return mItems.size();
    }

    private static void addNormalItem(List<Item> list, long id, SectionItem section) {
        NormalItem item = new NormalItem(section, id);
        section.addNormalItem(item);
        list.add(item);
    }
}
