/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "leaderboard")
public class leaderBoard {
	@Id
    @Column(name = "myrank")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer myrank;
	
	@Column(name = "username")
    @NotFound(action = NotFoundAction.IGNORE)
    private String user_name;
    
    @Column(name = "score")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer score;

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    
    public Integer getRank() {
        return this.myrank;
    }
    
    public  void setRank(Integer rank) {
        this.myrank = rank;
    }
    
    public String getUserName(){
    	return this.user_name;
    }

    public void setUserName(String username) {
    	this.user_name = username;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("rank",this.getScore().toString())
                .append("score", this.getScore()).toString();
        	
    }
}
