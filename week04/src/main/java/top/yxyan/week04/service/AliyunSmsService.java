package top.yxyan.week04.service;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.CheckSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.aliyun.teaopenapi.models.Config;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yxyan.week04.config.AliyunSmsProperties;
import top.yxyan.week04.exception.BusinessException;

/**
 * 阿里云短信服务类
 *
 * 功能：
 * 1.发送短信验证码（支持阿⾥云⾃动⽣成或⾃定义）
 * 2.核验短信验证码（仅限阿⾥云⽣成的验证码）
 *
 * @author yxyan
 * @date 2026/3/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliyunSmsService {
    private final AliyunSmsProperties smsProperties;

    /**
     * 创建短信认证客户端
     *
     * @return SMS Client 实例
     */
    private Client createClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(smsProperties.getAccessKeyId())
                .setAccessKeySecret(smsProperties.getAccessKeySecret());
        config.endpoint = smsProperties.getEndpoint();
        config.regionId = smsProperties.getRegionId();
        return new Client(config);
    }

    /**
     * 发送短信验证码（由阿⾥云⾃动⽣成验证码，⽀持后续核验）
     * <p>
     * 适⽤场景：需要阿⾥云帮助验证验证码的情况
     *
     * @param phone ⼿机号
     * @return 发送结果，含 VerifyCode（若 ReturnVerifyCode 为 true）
     */
    public SendSmsVerifyCodeResponse sendVerifyCodeAuto(String phone) throws Exception

    {
        // 使⽤ ##code## 占位符，由阿⾥云⽣成验证码，⽀持 CheckSmsVerifyCode 核验
        String templateParam = "{\"code\":\"##code##\",\"min\":\"5\"}";
        return sendVerifyCode(phone, templateParam, true);
    }

    /**
     * 发送短信验证码（通⽤⽅法）
     *
     * @param phone            ⼿机号
     * @param templateParam    模板参数 JSON
     *                         - 阿⾥云⽣成：{"code":"##code##","min":"5"}
     *                         - ⾃定义验证码：{"code":"123456","min":"5"}
     * @param returnVerifyCode 是否在响应中返回验证码（仅当使⽤ ##code## 时有
     *                         效）
     * @return 发送结果
     */
    public SendSmsVerifyCodeResponse sendVerifyCode(String phone, String templateParam, boolean returnVerifyCode) throws Exception {
        // 校验服务是否启⽤
        if (!smsProperties.isEnabled()) {
            throw new BusinessException(500, "短信服务未启⽤");
        }
        // 校验 AccessKey 是否配置
        if (smsProperties.getAccessKeyId() == null || smsProperties.getAccessKeyId().isBlank()) {
            throw new BusinessException(500, "短信服务未配置 AccessKey，请设置环境变量 ALIYUN_SMS_ACCESS_KEY_ID");
        }
        // 构建请求参数
        SendSmsVerifyCodeRequest request = new SendSmsVerifyCodeRequest()
                .setPhoneNumber(phone)
                .setSignName(smsProperties.getSignName())
                .setTemplateCode(smsProperties.getTemplateCode())
                .setTemplateParam(templateParam)
                .setCodeLength(6L) // 验证码⻓度：6位
                .setValidTime(300L) // 验证码有效期：300秒（5分钟）
                         .setDuplicatePolicy(1L) // 重复发送策略：1-允许重复
                         .setInterval(60L) // 发送间隔：60秒
                         .setCodeType(1L) // 验证码类型：1-数字
                         .setReturnVerifyCode(returnVerifyCode);

        // 设置⽅案名称（可选）
        if (smsProperties.getSchemeName() != null && !smsProperties.getSchemeName().isBlank()) {
            request.setSchemeName(smsProperties.getSchemeName());
        }
        // 发送短信
        Client client = createClient();
        SendSmsVerifyCodeResponse response = client.sendSmsVerifyCode(request);
        // 记录⽇志
        if (response.getBody() != null) {
            String bizCode = response.getBody().getCode();
            if ("OK".equals(bizCode)) {
                log.info("短信验证码发送成功，⼿机号={}, bizId={}",
                        maskPhone(phone),
                        response.getBody().getModel() != null ?
                                response.getBody().getModel().getBizId() : "");
            } else {
                log.warn("短信验证码发送失败，⼿机号={}, code={}, message= {}",
                        maskPhone(phone), bizCode, response.getBody().getMessage());
            }
        }
        return response;
    }

    /**
     * 核验短信验证码（仅当发送时使⽤ ##code## 由阿⾥云⽣成时有效）
     *
     * @param phone      ⼿机号
     * @param verifyCode ⽤户输⼊的验证码
     * @param outId      发送时返回的 OutId，可选
     * @return 核验结果，true 表示验证通过
     */
    public boolean checkVerifyCode(String phone, String verifyCode, String outId) throws Exception {
        if (!smsProperties.isEnabled()) {
            return false;
        }
        // 构建核验请求
        CheckSmsVerifyCodeRequest req = new CheckSmsVerifyCodeRequest()
                .setPhoneNumber(phone)
                .setVerifyCode(verifyCode);
        if (outId != null && !outId.isBlank()) {
            req.setOutId(outId);
        }
        if (smsProperties.getSchemeName() != null && !smsProperties.getSchemeName().isBlank()) {
            req.setSchemeName(smsProperties.getSchemeName());
        }
        // 执⾏核验
        Client client = createClient();
        var resp = client.checkSmsVerifyCode(req);
        if (resp.getBody() != null && resp.getBody().getModel() != null) {
            return "PASS".equals(resp.getBody().getModel().getVerifyResult());
        }
        return false;
    }

    /**
     * ⼿机号脱敏处理
     *
     * @param phone 原始⼿机号
     * @return 脱敏后的⼿机号，如 138****1234
     */
    private String maskPhone(String phone) {
        if (phone == null || phone.length() < 7) {
            return "***";
        }
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }
}
