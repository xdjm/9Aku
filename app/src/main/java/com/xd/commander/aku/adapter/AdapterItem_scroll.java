package com.xd.commander.aku.adapter;
import com.xd.commander.aku.bean.Section;
import java.util.ArrayList;
import java.util.List;

import cdflynn.android.library.scroller.SectionScrollAdapter;

public class AdapterItem_scroll implements SectionScrollAdapter {

    private List<Section> mSections;

    public AdapterItem_scroll(List<String> contacts) {
        initWithContacts(contacts);
    }

    @Override
    public int getSectionCount() {
        return mSections.size();
    }

    @Override
    public String getSectionTitle(int position) {
        return mSections.get(position).getTitle();
    }

    @Override
    public int getSectionWeight(int position) {
        return mSections.get(position).getWeight();
    }
    public Section fromItemIndex(int itemIndex) {
        for (Section s : mSections) {
            final int range = s.getIndex() + s.getWeight();
            if (itemIndex < range) {
                return s;
            }
        }
        return mSections.get(mSections.size() - 1);
    }

    public int positionFromSection(int sectionIndex) {
        return mSections.get(sectionIndex).getIndex();
    }

    public int sectionFromPosition(int positionIndex) {
        Section s;
        for (int i = 0; i < mSections.size(); i++) {
            s = mSections.get(i);
            final int range = s.getIndex() + s.getWeight();
            if (positionIndex < range) {
                return i;
            }
        }
        return mSections.size() - 1;
    }

    private void initWithContacts(List<String> contacts) {
        mSections = new ArrayList<>();
        String sectionTitle = null;
        String contact;
        int itemCount = 0;
        for (int i = 0; i < contacts.size(); i++) {
            contact = contacts.get(i);
            String firstLetter = contact.substring(0, 1);

            if (sectionTitle == null) {
                sectionTitle = firstLetter;
            }
            if (sectionTitle.compareTo(firstLetter) == 0) {
                itemCount++;
                continue;
            }

            mSections.add(new Section(i - itemCount, sectionTitle, itemCount));
            sectionTitle = firstLetter;
            itemCount = 1;
        }
    }
}
