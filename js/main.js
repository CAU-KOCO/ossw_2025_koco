document.addEventListener('DOMContentLoaded', function() {
    // 登录表单处理
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            // 模拟登录验证
            if (username && password) {
                // 获取用户数据
                const users = JSON.parse(localStorage.getItem('users') || '[]');
                const user = users.find(u => u.username === username && u.password === password);

                if (user) {
                    // 存储当前登录用户信息
                    localStorage.setItem('currentUser', JSON.stringify({
                        username: user.username,
                        email: user.email
                    }));
                    window.location.href = 'main.html';
                } else {
                    alert('사용자 이름 또는 비밀번호가 잘못되었습니다');
                }
            } else {
                alert('사용자 이름과 비밀번호를 입력하세요');
            }
        });
    }

    // 注册表单处理
    const registerForm = document.getElementById('register-form');
    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const username = document.getElementById('reg-username').value;
            const email = document.getElementById('reg-email').value;
            const password = document.getElementById('reg-password').value;

            // 模拟注册处理
            if (username && email && password) {
                // 获取现有用户数据
                const users = JSON.parse(localStorage.getItem('users') || '[]');

                // 检查用户名是否已存在
                if (users.some(u => u.username === username)) {
                    alert('이미 사용 중인 사용자 이름입니다');
                    return;
                }

                // 检查邮箱是否已存在
                if (users.some(u => u.email === email)) {
                    alert('이미 사용 중인 이메일입니다');
                    return;
                }

                // 添加新用户
                users.push({
                    username: username,
                    email: email,
                    password: password
                });

                // 保存用户数据
                localStorage.setItem('users', JSON.stringify(users));

                alert('등록 성공!');
                window.location.href = 'index.html';
            } else {
                alert('모든 필수 항목을 작성하세요');
            }
        });
    }

    // 个人资料表单处理
    const profileForm = document.getElementById('profile-form');
    if (profileForm) {
        // 获取当前用户信息并填充表单
        const currentUser = JSON.parse(localStorage.getItem('currentUser') || '{}');
        if (!currentUser.username) {
            // 未登录，重定向到登录页面
            window.location.href = 'index.html';
            return;
        }

        const profileUsername = document.getElementById('profile-username');
        const profileEmail = document.getElementById('profile-email');

        if (profileUsername && profileEmail) {
            profileUsername.value = currentUser.username;
            profileEmail.value = currentUser.email || '';
        }

        // 处理表单提交
        profileForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const newUsername = document.getElementById('profile-username').value;
            const newEmail = document.getElementById('profile-email').value;
            const newPassword = document.getElementById('profile-password').value;
            const confirmPassword = document.getElementById('profile-password-confirm').value;

            // 验证表单
            if (!newUsername || !newEmail) {
                alert('사용자 이름과 이메일은 필수 항목입니다');
                return;
            }

            // 如果输入了密码，验证两次输入是否一致
            if (newPassword && newPassword !== confirmPassword) {
                alert('비밀번호가 일치하지 않습니다');
                return;
            }

            // 获取用户数据
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const userIndex = users.findIndex(u => u.username === currentUser.username);

            if (userIndex === -1) {
                alert('사용자 정보를 찾을 수 없습니다');
                return;
            }

            // 检查新用户名是否已被其他用户使用
            if (newUsername !== currentUser.username &&
                users.some(u => u.username === newUsername)) {
                alert('이미 사용 중인 사용자 이름입니다');
                return;
            }

            // 检查新邮箱是否已被其他用户使用
            if (newEmail !== users[userIndex].email &&
                users.some(u => u.email === newEmail)) {
                alert('이미 사용 중인 이메일입니다');
                return;
            }

            // 更新用户信息
            users[userIndex].username = newUsername;
            users[userIndex].email = newEmail;

            // 如果输入了新密码，更新密码
            if (newPassword) {
                users[userIndex].password = newPassword;
            }

            // 保存更新后的用户数据
            localStorage.setItem('users', JSON.stringify(users));

            // 更新当前用户信息
            localStorage.setItem('currentUser', JSON.stringify({
                username: newUsername,
                email: newEmail
            }));

            alert('프로필이 성공적으로 업데이트되었습니다');
        });
    }

    // 退出登录
    const logoutBtn = document.getElementById('logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            localStorage.removeItem('currentUser');
            window.location.href = 'index.html';
        });
    }

    // 文件上传区域
    const uploadArea = document.getElementById('upload-area');
    const fileInput = document.getElementById('file-input');
    const uploadPlaceholder = document.getElementById('upload-placeholder');
    const uploadProgress = document.getElementById('upload-progress');

    if (uploadArea && fileInput) {
        // 确保初始状态正确
        uploadPlaceholder.style.display = 'flex';
        uploadProgress.style.display = 'none';

        // 点击上传区域触发文件选择
        uploadArea.addEventListener('click', function() {
            fileInput.click();
        });

        // 拖拽文件处理
        uploadArea.addEventListener('dragover', function(e) {
            e.preventDefault();
            uploadArea.classList.add('dragover');
        });

        uploadArea.addEventListener('dragleave', function() {
            uploadArea.classList.remove('dragover');
        });

        uploadArea.addEventListener('drop', function(e) {
            e.preventDefault();
            uploadArea.classList.remove('dragover');

            if (e.dataTransfer.files.length) {
                handleFile(e.dataTransfer.files[0]);
            }
        });

        // 文件选择处理
        fileInput.addEventListener('change', function() {
            if (fileInput.files.length) {
                handleFile(fileInput.files[0]);
            }
        });

        // 处理上传的文件
        function handleFile(file) {
            // 检查文件类型
            const validTypes = ['.txt', '.docx', 'text/plain', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'];
            const fileType = file.type || file.name.substring(file.name.lastIndexOf('.'));

            if (!validTypes.some(type => fileType.includes(type))) {
                alert('.txt 또는 .docx 파일을 업로드하세요');
                return;
            }

            // 显示加载动画
            uploadPlaceholder.style.display = 'none';
            uploadProgress.style.display = 'flex';

            // 模拟文件处理和分析
            setTimeout(function() {
                // 隐藏加载动画
                uploadProgress.style.display = 'none';
                uploadPlaceholder.style.display = 'block';

                // 更新分析结果和反馈
                const analysisResult = document.getElementById('analysis-result');
                const feedbackContent = document.getElementById('feedback-content');

                if (analysisResult && feedbackContent) {
                    analysisResult.innerHTML = `<p>파일 "${file.name}"의 분석 결과:</p><p>이것은 예시 분석 내용입니다. 실제 응용에서는 실제 분석 결과가 표시됩니다.</p>`;
                    feedbackContent.innerHTML = `<p>분석 기반 피드백:</p><p>이것은 예시 피드백 내용입니다. 실제 응용에서는 실제 피드백 내용이 표시됩니다.</p>`;

                    // 保存记录（模拟）- 使用实际显示的内容
                    saveRecord(file.name, analysisResult.innerHTML, feedbackContent.innerHTML);
                }

            }, 2000); // 模拟2秒的处理时间
        }

        // 保存记录（模拟）- 修改函数参数和实现
        // 保存记录（模拟）- 修改函数参数和实现
        // 保存记录时正确设置了timestamp
        function saveRecord(fileName, analysisContent, feedbackContent) {
        const records = JSON.parse(localStorage.getItem('records') || '[]');
        const now = Date.now();
        records.push({
        id: now,
        fileName: fileName,
        analysis: analysisContent,
        feedback: feedbackContent,
        timestamp: now,              // 使用timestamp字段进行排序
        date: new Date().toLocaleString('ko-KR')
        });
        localStorage.setItem('records', JSON.stringify(records));
        }
        
        // 显示记录时正确进行排序
        function displayRecords() {
        const recordsContainer = document.querySelector('.records-container');
        if (!recordsContainer) return;
    
            const records = JSON.parse(localStorage.getItem('records') || '[]')
                .sort((a, b) => {
                    const timeA = a.timestamp || a.id || 0;
                    const timeB = b.timestamp || b.id || 0;
                    return timeB - timeA;  // 降序排序，最新的在前
                });
    
            // 清空现有内容
            const recordsList = recordsContainer.querySelector('.records-list') || document.createElement('div');
            recordsList.className = 'records-list';
            recordsList.innerHTML = '';
    
            // 添加记录
            records.forEach(record => {
                const recordItem = document.createElement('div');
                recordItem.className = 'record-item';
    
                // 格式化日期
                const date = new Date(record.timestamp);
                const formattedDate = `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 ${date.getHours()}:${date.getMinutes()}`;
    
                recordItem.innerHTML = `
                    <div class="record-header">
                        <span class="record-date">${formattedDate}</span>
                        <a href="detail.html?id=${record.id}" class="btn btn-small">
                            <i class="fas fa-eye"></i> 상세 보기
                        </a>
                    </div>
                    <div class="record-content">
                        <div class="record-analysis">
                            <h4>분석 결과</h4>
                            <div class="record-text highlighted">${record.analysis || record.analysisContent}</div>
                        </div>
                        <div class="record-feedback">
                            <h4>피드백</h4>
                            <div class="record-text">${record.feedback || record.feedbackContent}</div>
                        </div>
                    </div>
                `;
                recordsList.appendChild(recordItem);
            });

            // 如果recordsList不在container中，添加它
            if (!recordsContainer.contains(recordsList)) {
                recordsContainer.appendChild(recordsList);
            }
        }

        // 在页面加载时显示记录
        if (document.querySelector('.records-container')) {
            displayRecords();
        }
    }

    // 详情页面处理
    if (window.location.pathname.includes('detail.html')) {
        const urlParams = new URLSearchParams(window.location.search);
        const recordId = urlParams.get('id');

        if (recordId) {
            const records = JSON.parse(localStorage.getItem('records') || '[]');
            const record = records.find(r => r.id == recordId);

            if (record) {
                const detailAnalysis = document.getElementById('detail-analysis');
                const detailFeedback = document.getElementById('detail-feedback');
                const detailDate = document.querySelector('.detail-date');

                if (detailAnalysis && detailFeedback && detailDate) {
                    detailAnalysis.innerHTML = `<p>${record.analysisContent}</p>`;
                    detailFeedback.innerHTML = `<p>${record.feedbackContent}</p>`;
                    detailDate.textContent = record.timestamp;
                }
            }
        }
    }

    // 记录页面处理
    if (window.location.pathname.includes('records.html')) {
        const recordsContainer = document.querySelector('.records-container');
        const records = JSON.parse(localStorage.getItem('records') || '[]');

        if (recordsContainer && records.length > 0) {
            // 清除示例记录
            const existingRecords = recordsContainer.querySelectorAll('.record-item');
            existingRecords.forEach(item => item.remove());

            // 按时间排序，最新的记录在最上面
            records.sort((a, b) => {
                return new Date(b.date) - new Date(a.date);
            });

            // 添加实际记录
            records.forEach(record => {
                const recordItem = document.createElement('div');
                recordItem.className = 'record-item';
                recordItem.innerHTML = `
                    <div class="record-header">
                        <span class="record-date">${record.date}</span>
                        <a href="detail.html?id=${record.id}" class="btn btn-small"><i class="fas fa-eye"></i> 상세 보기</a>
                    </div>
                    <div class="record-content">
                        <div class="record-analysis">
                            <h4>분석 결과</h4>
                            <div class="record-text highlighted">${record.analysis}</div>
                        </div>
                        <div class="record-feedback">
                            <h4>피드백</h4>
                            <div class="record-text">${record.feedback}</div>
                        </div>
                    </div>
                `;
                recordsContainer.appendChild(recordItem);
            });
        }
    }
});