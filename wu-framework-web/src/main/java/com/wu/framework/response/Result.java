package com.wu.framework.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.framework.response.mark.JsonViewType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.DERNull;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonView(JsonViewType.Default.class)
public class Result<T> {

    private Integer code;

    private String message;

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> ext;

    public Result(Integer code) {
        this.code = code;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return DefaultResultCode.SUCCESS.code.equals(code);
    }

    /**
     * 请求成功并且数据不为空
     *
     * @return 是否成功并且有数据
     */
    public boolean isSuccessAndHasData() {
        return DefaultResultCode.SUCCESS.code.equals(code) && !ObjectUtils.isEmpty(data);
    }

    public boolean hasSuccessData() {
        return DefaultResultCode.SUCCESS.code.equals(code) && null != data;
    }

    /**
     * 转换成你想要的数据类型
     *
     * @param mapper 转换器
     * @param <R>    返回结果类型
     * @return
     */
    public <R> Result<R> convert(Function<? super T, ? extends R> mapper) {
        if (isSuccess()) {
            if (ObjectUtils.isEmpty(this.data)) {
                return ResultFactory.successOf();
            }
            R apply = mapper.apply(this.data);
            return ResultFactory.successOf(apply);
        }
        return ResultFactory.errorOf(this);
    }

    /**
     * 处理数据后返回 结果 Result
     *
     * @param apply 处理方法
     * @return 结果 Result
     */
    public <R> Result<R> apply(Function<? super T, ? extends Result<R>> apply) {
        if (isSuccessAndHasData()) {
            if (ObjectUtils.isEmpty(this.data)) {
                return ResultFactory.successOf();
            }
            return apply.apply(this.data);
        }
        return ResultFactory.errorOf(this);

    }

    /**
     * 处理数据后返回 结果 T
     *
     * @param apply 处理方法
     * @return 结果 T
     */
    public <R> R applyOther(Function<? super T, ? extends R> apply) {
        if (isSuccessAndHasData()) {
            if (ObjectUtils.isEmpty(this.data)) {
                return null;
            }
            return apply.apply(this.data);
        }
        return null;
    }

    /**
     * 获取数据后操作
     *
     * @param consumer 数据消费者
     */
    public void accept(Consumer<? super T> consumer) {
        if (isSuccessAndHasData()) {
            consumer.accept(this.data);
        }
    }
}
