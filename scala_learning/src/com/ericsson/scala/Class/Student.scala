package com.ericsson.scala.Class

import scala.beans.BeanProperty

class Student {
  @BeanProperty var name: String = _
  @BeanProperty var id: String = _

  private var privateAge = 0

  def age = privateAge
  def age_=(newAge: Int) = if (newAge < 0) privateAge = 0 else if (newAge > privateAge) privateAge = newAge
}

object Student extends App {
  val s = new Student
  s.age
  s.age = 30
  s.setName("eqinson")
  s.getName
}

/*
λ javap Student.class                                                         
Compiled from "Student.scala"                                                 
public class com.ericsson.scala.chapter5.Student {                            
  public static void main(java.lang.String[]);                                
  public static void delayedInit(scala.Function0<scala.runtime.BoxedUnit>);   
  public static java.lang.String[] args();                                    
  public static void scala$App$_setter_$executionStart_$eq(long);             
  public static long executionStart();                                        
  public static void delayedEndpoint$com$ericsson$scala$chapter5$Student$1(); 
  public static com.ericsson.scala.chapter5.Student s();                      
  public java.lang.String name();                                             
  public void name_$eq(java.lang.String);                                     
  public void setName(java.lang.String);                                      
  public java.lang.String id();                                               
  public void id_$eq(java.lang.String);                                       
  public void setId(java.lang.String);                                        
  public int age();                                                           
  public void age_$eq(int);                                                   
  public java.lang.String getName();                                          
  public java.lang.String getId();                                            
  public com.ericsson.scala.chapter5.Student();                               
}                                                                             
*/