package com.smart.algorithm.design.visitor;

/**
 * accept() 接受将要访问它的对象，
 * getSubject() 获取将要被访问的属性
 * Created by fc.w on 2017/9/7.
 */
public interface Subject {

    void accept(Visitor visitor);

    String getSubject();

}
