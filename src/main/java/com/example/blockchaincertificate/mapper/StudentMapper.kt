package com.example.blockchaincertificate.mapper

import com.example.blockchaincertificate.Student
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import tk.mybatis.mapper.common.Mapper

/**
 * Created by guo on 2018/7/27.
 * desc:
 */
@org.apache.ibatis.annotations.Mapper
interface StudentMapper : Mapper<Student> {

    @Select("SELECT * FROM student WHERE status = #{status}")
    fun findByStatus(status: Int): List<Student>


    @Select("SELECT * FROM student WHERE status = #{status} AND jyj_status = #{jyjStatus}")
    fun findByJyjStatus(@Param("status") status: Int
                        , @Param("jyjStatus") jyjStatus: Int): List<Student>
}