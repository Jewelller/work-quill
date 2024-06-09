package com.qwizery.work_quill.server.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qwizery.work_quill.server.service.mapper.MemberMapper;
import com.qwizery.work_quill.server.service.entity.Member;
import com.qwizery.work_quill.server.service.service.MemberService;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

}

