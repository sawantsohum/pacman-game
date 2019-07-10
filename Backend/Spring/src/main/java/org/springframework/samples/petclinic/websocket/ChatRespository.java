package org.springframework.samples.petclinic.websocket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Method that links to the Chat database where the history will be stored.
 *
 */
@Repository
public interface ChatRespository extends JpaRepository<Chat, Integer> 
{
}