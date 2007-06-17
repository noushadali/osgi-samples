package com.osgisamples.congress.business.impl;

import java.util.HashSet;
import java.util.Set;

import com.osgisamples.congress.business.CongressManager;
import com.osgisamples.congress.business.CongressNotFoundException;
import com.osgisamples.congress.business.RegistrantNotFoundForCongressException;
import com.osgisamples.congress.business.RegistrantValidationException;
import com.osgisamples.congress.business.SessionNotAvailableForCongressException;
import com.osgisamples.congress.business.SessionValidationException;
import com.osgisamples.congress.dataaccess.CongressDao;
import com.osgisamples.congress.dataaccess.RegistrantDao;
import com.osgisamples.congress.dataaccess.SessionDao;
import com.osgisamples.congress.dataaccess.exceptions.ObjectRetrievalFailureException;
import com.osgisamples.congress.dataaccess.exceptions.SessionAllreadyExistsException;
import com.osgisamples.congress.domain.Congress;
import com.osgisamples.congress.domain.CongressRegistration;
import com.osgisamples.congress.domain.CongressRole;
import com.osgisamples.congress.domain.ListenerSessionRegistration;
import com.osgisamples.congress.domain.Participant;
import com.osgisamples.congress.domain.Registrant;
import com.osgisamples.congress.domain.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CongressManagerImpl implements CongressManager {
    private final Logger logger = LoggerFactory.getLogger(CongressManagerImpl.class);
    private CongressDao congressDao;
    private RegistrantDao registrantDao;
    private SessionDao sessionDao;
    
    public CongressManagerImpl(final CongressDao congressDao, final RegistrantDao registrantDao, final SessionDao sessionDao) {
        this.congressDao = congressDao;
        this.registrantDao = registrantDao;
        this.sessionDao = sessionDao;
    }
    
    public void storeCongress(Congress congress) {
        congressDao.storeCongress(congress);
    }
    
    public String registerNewRegistrantForCongress(final Registrant registrant, Congress searchCongress)
            throws CongressNotFoundException, RegistrantValidationException {
        Congress congress = loadCongress(searchCongress);
        Registrant toRegisterRegistrant;
        try {
            toRegisterRegistrant = registrantDao.loadRegistrantByRegistrationNumber(registrant.getRegistrationNumber());
        } catch (ObjectRetrievalFailureException e) {
            logger.debug(e.getMessage());
            registrantDao.storeRegistrant(registrant);
            toRegisterRegistrant = registrant;
        }
        
        CongressRegistration congressRegistration = new CongressRegistration(congress,toRegisterRegistrant);
        congressDao.storeCongressRegistration(congressRegistration);
        return toRegisterRegistrant.getRegistrationNumber();
    }
    
    public Set<Registrant> listAllRegistrantsForCongress(final Congress searchCongress) throws CongressNotFoundException {
        Congress congress = loadCongress(searchCongress);
        Set<CongressRegistration> congressRegistrations = congress.getRegistrations();
        Set<Registrant> registrants = new HashSet<Registrant>();
        for (CongressRegistration congressRegistration : congressRegistrations) {
            registrants.add(congressRegistration.getRegistrant());
        }
        return registrants;
    }
    
    private Congress loadCongress(Congress searchCongress) throws CongressNotFoundException {
        Congress congress;
        try {
            if (searchCongress.getId() != null) {
                congress = congressDao.loadCongress(searchCongress.getId());
            } else {
                congress = congressDao.loadCongressByName(searchCongress.getName());
            }
        } catch (ObjectRetrievalFailureException e) {
            logger.info(e.getMessage());
            throw new CongressNotFoundException(e.getMessage());
        }
        return congress;
    }
    
    public String registerNewSessionForCongress(Session session, Congress searchCongress) throws CongressNotFoundException, SessionValidationException {
        Congress congress = loadCongress(searchCongress);
        Session registeredSession;
        try {
            registeredSession = sessionDao.createSession(session);
        } catch (SessionAllreadyExistsException e) {
            logger.info("Trying to register a session with a name that allready exists : " + session.getName());
            throw new SessionValidationException("The Session with name (" + session.getName() + ") allready exists");
        }
        registeredSession.setCongress(congress);
        congress.getSessions().add(registeredSession);
        return registeredSession.getId().toString();
    }
    
    public Set<Session> listAllSessionsForCongress(Congress searchCongress) throws CongressNotFoundException {
        Congress congress = loadCongress(searchCongress);
        return congress.getSessions();
    }
    
    public void updateSessionsForParticipantOfCongress(Congress searchCongress, Registrant searchRegistrant, Set<Session> searchSessions)
            throws CongressNotFoundException, SessionNotAvailableForCongressException, RegistrantNotFoundForCongressException {
        Congress congress = loadCongress(searchCongress);
        Registrant registrant = checkRegistrationForRegistrantAndCongress(searchRegistrant, congress);
        Set<Session> foundSessions = new HashSet<Session>();
        for (Session session : searchSessions) {
            foundSessions.add(checkRegistrationForSessionAndCongress(session, congress));
        }
        // if all sessions are valid, register for these sessions
        for (Session session : foundSessions) {
            Set<ListenerSessionRegistration> listeners = session.getListeners();
            boolean allreadyRegistered = false;
            for (ListenerSessionRegistration registration : listeners) {
                if (registration.getListener().getRegistrant().equals(registrant)) {
                    allreadyRegistered = true;
                    break;
                }
            }
            if (!allreadyRegistered) {
                Set<CongressRole> congressRoles = registrant.getCongressRoles();
                Participant foundParticipant = null;
                for (CongressRole role : congressRoles) {
                    if (role instanceof Participant) {
                        foundParticipant = (Participant) role;
                        break;
                    }
                }
                if (foundParticipant == null) {
                    throw new RegistrantValidationException("Current Registrant does not have the Participant Role");
                }
                sessionDao.createListenerSessionRegistration(session, foundParticipant);
            }
        }
    }
    
    private Registrant checkRegistrationForRegistrantAndCongress(Registrant searchRegistrant, Congress congress) {
        Registrant foundRegistrant = null;
        if (searchRegistrant.getRegistrationNumber() != null && !"".equals(searchRegistrant.getRegistrationNumber())) {
            Set<CongressRegistration> congressRegistrations = congress.getRegistrations();
            for (CongressRegistration congressRegistration : congressRegistrations) {
                if (congressRegistration.getRegistrant().getRegistrationNumber().equals(searchRegistrant.getRegistrationNumber())) {
                    foundRegistrant = congressRegistration.getRegistrant();
                    break;
                }
            }
        } else if (searchRegistrant.getEmailAddress() != null && !"".equals(searchRegistrant.getEmailAddress())) {
            Set<CongressRegistration> congressRegistrations = congress.getRegistrations();
            for (CongressRegistration congressRegistration : congressRegistrations) {
                if (congressRegistration.getRegistrant().getEmailAddress().equals(searchRegistrant.getEmailAddress())) {
                    foundRegistrant = congressRegistration.getRegistrant();
                    break;
                }
            }
        } else {
            throw new RegistrantNotFoundForCongressException("Missing required identifier for Registrant like registration number or email address");
        }
        if (foundRegistrant == null) {
            throw new RegistrantNotFoundForCongressException("No matching registrant is found for the provided congress");
        }
        return foundRegistrant;
    }
    
    private Session checkRegistrationForSessionAndCongress(Session searchSession, Congress congress) {
        Session foundSession = null;
        if (searchSession.getId() != null) {
            Set<Session> sessions = congress.getSessions();
            for (Session session : sessions) {
                if (session.getId().equals(searchSession.getId())) {
                    foundSession = session;
                    break;
                }
            }
        } else if (searchSession.getName() != null && !"".equals(searchSession.getName())) {
            Set<Session> sessions = congress.getSessions();
            for (Session session : sessions) {
                if (session.getName().equals(searchSession.getName())) {
                    foundSession = session;
                    break;
                }
            }
        } else {
            throw new SessionNotAvailableForCongressException("The provided session does not contain the required identifier fields : id or name");
        }
        if (foundSession == null) {
            throw new SessionNotAvailableForCongressException("The provided session is not registered for the congress");
        }
        return foundSession;
    }
}
