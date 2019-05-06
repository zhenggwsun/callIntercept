package com.eric.callintercept.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.eric.callintercept.R;
import com.eric.callintercept.adapter.ContactsAdapter;
import com.eric.callintercept.dao.object.ContactModel;
import com.eric.callintercept.dao.object.PhoneNumberDO;
import com.eric.callintercept.dao.operater.ContactsOperater;
import com.eric.callintercept.dao.operater.PhoneNumberOperater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Add_from_contacts extends AppCompatActivity {

    private ListView listView;

    private Button add;

    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_from_contacts);

        init();
        showList();
    }

    private void showList() {
        List<ContactModel> callLogModels = ContactsOperater.read(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        ContactsAdapter contactsAdapter = new ContactsAdapter(callLogModels, layoutInflater);
        listView.setAdapter(contactsAdapter);
    }

    private void init() {
        listView = findViewById(R.id.list_view_contacts);
        add = findViewById(R.id.button_add);
        cancel = findViewById(R.id.button_cancel);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(ContactsAdapter.addMap);
                addNumber(deleteDuplicated());
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 使用set去重
     * @return
     */
    private Set<String> deleteDuplicated() {
        Set<String> stringSet = new HashSet<String>();
        Collection<String> values = ContactsAdapter.addMap.values();
        Iterator<String> iterator = values.iterator();
        while (iterator.hasNext()){
            String number = iterator.next();
            stringSet.add(number);
        }
        return stringSet;
    }

    private void addNumber(Set<String> insertNumbers){
        //重复号码去除
        //类型获取,BLACK_LIST/ALLOW_LIST
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String type = PhoneNumberOperater.BLACK_LIST;
        if (Allow_list.ALLOW_LIST.equals(from)){
            type = PhoneNumberOperater.ALLOW_LIST;
        }
        List<PhoneNumberDO> inRecords = PhoneNumberOperater.read(this, type);

        Iterator<PhoneNumberDO> iterator = inRecords.iterator();
        while(iterator.hasNext()){
            PhoneNumberDO phoneNumberDO = iterator.next();
            if (insertNumbers.contains(phoneNumberDO.getPhoneNumber())){
                insertNumbers.remove(phoneNumberDO.getPhoneNumber());
            }
        }
        List<PhoneNumberDO> phoneNumberDOS = convert2PhoneNumberList(insertNumbers, type);
        PhoneNumberOperater.write(this, phoneNumberDOS);
    }

    /**
     * 转换
     * @param insertNumbers
     * @return
     */
    private List<PhoneNumberDO> convert2PhoneNumberList(Set<String> insertNumbers, String type) {
        List<PhoneNumberDO> phoneNumberDOS = new ArrayList<PhoneNumberDO>();
        Iterator<String> iterator = insertNumbers.iterator();
        while (iterator.hasNext()){
            String number = iterator.next();
            PhoneNumberDO numberDO = new PhoneNumberDO(null, number, type);
            phoneNumberDOS.add(numberDO);
        }
        return phoneNumberDOS;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ContactsAdapter.addMap.clear();
    }

}
