package com.example.SchoolWebApp.service;

import com.example.SchoolWebApp.constants.EazySchoolConstants;
import com.example.SchoolWebApp.model.Contact;
import com.example.SchoolWebApp.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    /**
     * Save Contact Details into DB
     * @param contact
     * @return boolean
     */
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(EazySchoolConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if(null != savedContact && savedContact.getContactId()>0) {
            isSaved = true;
        }
        return isSaved;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, sortDir.equals("asc")? Sort.by(sortField).ascending():Sort.by(sortField).descending());
//        Page<Contact> msgPage = contactRepository.findByStatus(EazySchoolConstants.OPEN, pageable);
        Page<Contact> msgPage = contactRepository.findOpenMsgs(EazySchoolConstants.OPEN, pageable);
        return msgPage;
    }

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
//        int rows = contactRepository.updateStatusById(EazySchoolConstants.CLOSE, contactId);
        int rows = contactRepository.updateMsgStatus(EazySchoolConstants.CLOSE, contactId);
        if(rows > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }
}
