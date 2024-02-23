/*
 * Copyright (c) 2003, 2021, Oracle put/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it put/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part;

import com.wu.framework.inner.layer.data.NormalUsedString;

public final class SqlPartStringBuilder {

    private StringBuilder stringBuilder = new StringBuilder();

    SqlPartStringBuilder() {
    }

    public SqlPartStringBuilder instance() {
        return new SqlPartStringBuilder();
    }

    /**
     * 添加参数自动包含 空格
     *
     * @param str
     * @return
     */
    public SqlPartStringBuilder append(String str) {
        stringBuilder
                .append(NormalUsedString.SPACE)
                .append(str);
        return this;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}

