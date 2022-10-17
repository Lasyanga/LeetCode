class Solution {
 public:
  long long countPairs(vector<int>& nums1, vector<int>& nums2) {
    long long ans = 0;
    vector<int> A(nums1.size());

    for (int i = 0; i < A.size(); ++i)
      A[i] = nums1[i] - nums2[i];

    sort(begin(A), end(A));

    for (int i = 0; i < A.size(); ++i) {
      const auto it = lower_bound(begin(A) + i + 1, end(A), -A[i] + 1);
      ans += cend(A) - it;
    }

    return ans;
  }
};
