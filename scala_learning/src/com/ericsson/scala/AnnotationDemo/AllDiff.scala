package com.ericsson.scala.AnnotationDemo

object AllDiff extends App {
  def allDifferent[@specialized T](x: T, y: T, z: T) = x != y && x != z && y != z
}

/*
λ javap AllDiff$.class
Compiled from "AllDiff.scala"
public final class com.ericsson.scala.chapter15.AllDiff$ implements scala.App {
  public static final com.ericsson.scala.chapter15.AllDiff$ MODULE$;
  public static {};
  public long executionStart();
  public java.lang.String[] scala$App$$_args();
  public void scala$App$$_args_$eq(java.lang.String[]);
  public scala.collection.mutable.ListBuffer<scala.Function0<scala.runtime.BoxedUnit>> scala$App$$initCode();
  public void scala$App$_setter_$executionStart_$eq(long);
  public void scala$App$_setter_$scala$App$$initCode_$eq(scala.collection.mutable.ListBuffer);
  public java.lang.String[] args();
  public void delayedInit(scala.Function0<scala.runtime.BoxedUnit>);
  public void main(java.lang.String[]);
  public <T extends java/lang/Object> boolean allDifferent(T, T, T);
  public boolean allDifferent$mZc$sp(boolean, boolean, boolean);
  public boolean allDifferent$mBc$sp(byte, byte, byte);
  public boolean allDifferent$mCc$sp(char, char, char);
  public boolean allDifferent$mDc$sp(double, double, double);
  public boolean allDifferent$mFc$sp(float, float, float);
  public boolean allDifferent$mIc$sp(int, int, int);
  public boolean allDifferent$mJc$sp(long, long, long);
  public boolean allDifferent$mSc$sp(short, short, short);
  public boolean allDifferent$mVc$sp(scala.runtime.BoxedUnit, scala.runtime.BoxedUnit, scala.runtime.BoxedUnit);
}
*/