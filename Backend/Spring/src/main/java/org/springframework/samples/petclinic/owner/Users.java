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
@Table(name = "users")
public class Users implements Comparable {
	@Column(name = "user_name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String user_name;
	
	@Id
    @Column(name = "myrank")
    @NotFound(action = NotFoundAction.IGNORE)
    private int myrank;
    
    @Column(name = "score")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer score;

    @Column(name = "password")
    @NotFound(action = NotFoundAction.IGNORE)
    private String password;
    
    

 
    public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Integer getRank() {
		return myrank;
	}


	public void setRank(Integer rank) {
		this.myrank = rank;
	}


	public String getUser_name() {
		return user_name;
	}


	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}





    @Override
    public String toString() {
        return new ToStringCreator(this)

                //.append("id", this.getId())
               
                .append("userName", this.getUser_name())
                .append("rank", this.getRank().toString())
                .append("Score", this.getScore().toString())
                .append("password", this.getPassword()).toString();
    }


    public int compareTo(Users compare) {
        int comparerank=((Users)compare).getRank();
        return comparerank - this.getRank();

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }


	@Override
	public int compareTo(Object o) {
		int comparerank=((Users)o).getRank();
        return comparerank - this.getRank();
	}
}
